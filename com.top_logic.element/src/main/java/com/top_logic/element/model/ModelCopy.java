/*
 * SPDX-FileCopyrightText: 2024 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.element.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import com.top_logic.basic.UnreachableAssertion;
import com.top_logic.basic.col.Provider;
import com.top_logic.basic.config.TypedConfiguration;
import com.top_logic.element.model.ModelCopy.Async.AsyncConsumer;
import com.top_logic.model.TLAssociation;
import com.top_logic.model.TLAssociationEnd;
import com.top_logic.model.TLClass;
import com.top_logic.model.TLClassPart;
import com.top_logic.model.TLClassifier;
import com.top_logic.model.TLEnumeration;
import com.top_logic.model.TLModel;
import com.top_logic.model.TLModelPart;
import com.top_logic.model.TLModule;
import com.top_logic.model.TLPrimitive;
import com.top_logic.model.TLReference;
import com.top_logic.model.TLStructuredTypePart;
import com.top_logic.model.TLType;
import com.top_logic.model.annotate.TLAnnotation;
import com.top_logic.model.impl.TLModelImpl;
import com.top_logic.model.impl.util.TLCharacteristicsCopier;

/**
 * Algorithm for deep cloning models.
 */
public class ModelCopy {

	private final Map<TLType, TLType> _typeMapping = new HashMap<>();

	private Map<TLType, Consumer<TLType>> _typeContinuations = new HashMap<>();

	private Set<TLClass> _completed = new HashSet<>();

	private Map<TLClass, Consumer<TLClass>> _completionContinuations = new HashMap<>();

	/**
	 * Creates a transient copy of the given {@link TLModel}.
	 */
	public static TLModel copy(TLModel model) {
		TLModelImpl result = new TLModelImpl();

		new ModelCopy().copy(model, result);

		return result;
	}

	/**
	 * Copies all contents from the source model to the target model.
	 */
	public void copy(TLModel source, TLModel target) {
		copyAnnotations(source, target);
		for (TLModule sourceModule : source.getModules()) {
			TLModule targetModule = target.addModule(target, sourceModule.getName());
			copy(sourceModule, targetModule);
		}
		if (!_typeContinuations.isEmpty()) {
			throw new IllegalArgumentException(
				"References to undefined types in source model: " + _typeContinuations.keySet());
		}
	}

	private void copy(TLModule source, TLModule target) {
		copyAnnotations(source, target);

		for (TLType sourceType : source.getTypes()) {
			switch (sourceType.getModelKind()) {
				case CLASS: {
					TLClass targetType = target.getModel().addClass(target, target, sourceType.getName());
					copy((TLClass) sourceType, targetType);
					break;
				}
				case DATATYPE: {
					TLPrimitive dataType = (TLPrimitive) sourceType;
					TLPrimitive targetType = target.getModel().addDatatype(target, target, sourceType.getName(),
						dataType.getKind(), dataType.getStorageMapping());
					copy(dataType, targetType);
					break;
				}
				case ENUMERATION:
					TLEnumeration targetType = target.getModel().addEnumeration(target, target, sourceType.getName());
					copy((TLEnumeration) sourceType, targetType);
					break;
				case ASSOCIATION:
					TLAssociation targetAssociation =
						target.getModel().addAssociation(target, target, sourceType.getName());
					copy((TLAssociation) sourceType, targetAssociation);
					break;

				default:
					throw new UnreachableAssertion("No such type expected: " + sourceType);
			}
		}
	}

	private void copy(TLAssociation source, TLAssociation target) {
		copyAnnotations(source, target);

		Async async = Async.SYNC;
		for (TLStructuredTypePart sourcePart : source.getLocalParts()) {
			switch (sourcePart.getModelKind()) {
				case PROPERTY: {
					async = async.join(map(sourcePart.getType(), t -> {
						TLStructuredTypePart targetProperty =
							target.getModel().addAssociationProperty(target, sourcePart.getName(), t);
						copy(sourcePart, targetProperty);
					}));
					break;
				}
				case END: {
					async = async.join(map(sourcePart.getType(), t -> {
						TLStructuredTypePart targetProperty =
							target.getModel().addAssociationEnd(target, sourcePart.getName(), t);
						copy(sourcePart, targetProperty);
					}));
					break;
				}
				default:
					throw new UnreachableAssertion("No such part expected: " + sourcePart);
			}

		}

		async.await(() -> enter(source, target));
	}

	private void copy(TLEnumeration source, TLEnumeration target) {
		copyAnnotations(source, target);
		for (TLClassifier sourceClassifier : source.getClassifiers()) {
			TLClassifier targetClassifier = target.getModel().createClassifier(sourceClassifier.getName());
			copy(sourceClassifier, targetClassifier);
			target.getClassifiers().add(targetClassifier);
		}
		enter(source, target);
	}

	private void copy(TLClassifier source, TLClassifier target) {
		copyAnnotations(source, target);
	}

	private void copy(TLPrimitive source, TLPrimitive target) {
		copyAnnotations(source, target);
		target.setBinary(source.isBinary());
		target.setDBPrecision(source.getDBPrecision());
		target.setDBSize(source.getDBSize());
		target.setDBType(source.getDBType());
		enter(source, target);
	}

	private void copy(TLClass source, TLClass target) {
		copyAnnotations(source, target);

		target.setAbstract(source.isAbstract());

		appendGeneralizations(target, source.getGeneralizations().iterator()).await(() -> {
			enter(source, target);

			awaitGeneralizations(target).await(() -> {
				Async partCopy = appendParts(target, source.getLocalClassParts().iterator());

				partCopy.await(() -> complete(target));
			});
		});
	}

	private Async awaitGeneralizations(TLClass target) {
		Async async = Async.SYNC;
		for (TLClass generalization : target.getGeneralizations()) {
			async = async.join(awaitCompletion(generalization));
		}
		return async;
	}

	private Async awaitCompletion(TLClass type) {
		if (_completed.contains(type)) {
			return Async.SYNC;
		}

		AsyncConsumer<TLClass> continuation = Async.whenAccepted();
		Consumer<TLClass> clash = _completionContinuations.put(type, continuation);
		if (clash != null) {
			_completionContinuations.put(type, clash.andThen(continuation));
		}

		return continuation;
	}

	private void complete(TLClass target) {
		_completed.add(target);
		Consumer<TLClass> continuation = _completionContinuations.remove(target);
		if (continuation != null) {
			continuation.accept(target);
		}
	}

	private Async appendParts(TLClass target, Iterator<TLClassPart> sourceParts) {
		if (!sourceParts.hasNext()) {
			return Async.SYNC;
		}

		TLStructuredTypePart sourcePart = sourceParts.next();
		Async step;
		switch (sourcePart.getModelKind()) {
			case PROPERTY: {
				step = map(sourcePart.getType(), t -> {
					TLStructuredTypePart targetProperty =
						target.getModel().addClassProperty(target, sourcePart.getName(), t);
					copy(sourcePart, targetProperty);
				});
				break;
			}
			case REFERENCE: {
				TLReference sourceReference = (TLReference) sourcePart;
				step = map(sourceReference.getEnd(), t -> {
					TLReference targetReference =
						target.getModel().addReference(target, sourcePart.getName(), t);
					copy(sourceReference, targetReference);
				});
				break;
			}
			default:
				throw new UnreachableAssertion("No such part expected: " + sourcePart);
		}
		return step.await(() -> appendParts(target, sourceParts));
	}

	private Async appendGeneralizations(TLClass target, Iterator<TLClass> sourceGeneralizations) {
		if (!sourceGeneralizations.hasNext()) {
			return Async.SYNC;
		}

		Async step = map(sourceGeneralizations.next(), t -> {
			target.getGeneralizations().add((TLClass) t);
		});

		return step.await(() -> appendGeneralizations(target, sourceGeneralizations));
	}

	private void copy(TLStructuredTypePart source, TLStructuredTypePart target) {
		copyAnnotations(source, target);

		TLCharacteristicsCopier.copyCharacteristics(source, target);
	}

	private void copy(TLReference source, TLReference target) {
		copyAnnotations(source, target);

		TLCharacteristicsCopier.copyCharacteristics(source, target);
	}

	private void copyAnnotations(TLModelPart source, TLModelPart target) {
		for (TLAnnotation annotation : source.getAnnotations()) {
			target.setAnnotation(TypedConfiguration.copy(annotation));
		}
	}

	private Async map(TLAssociationEnd sourceEnd, Consumer<TLAssociationEnd> continuation) {
		TLAssociation source = sourceEnd.getOwner();
		return map(source, target -> {
			List<? extends TLStructuredTypePart> targetEnds = ((TLAssociation) target).getLocalParts();
			int sourceIndex = source.getLocalParts().indexOf(sourceEnd);
			TLAssociationEnd targetEnd = (TLAssociationEnd) targetEnds.get(sourceIndex);

			continuation.accept(targetEnd);
		});
	}

	private Async map(TLType type, Consumer<TLType> continuation) {
		TLType result = _typeMapping.get(type);
		if (result == null) {
			AsyncConsumer<TLType> async = Async.whenAccepted(continuation);
			Consumer<TLType> clash = _typeContinuations.put(type, async);
			if (clash != null) {
				_typeContinuations.put(type, clash.andThen(async));
			}

			return async;
		} else {
			continuation.accept(result);
			return Async.SYNC;
		}
	}

	private void enter(TLType source, TLType target) {
		_typeMapping.put(source, target);
		Consumer<TLType> continuation = _typeContinuations.remove(source);
		if (continuation != null) {
			continuation.accept(target);
		}
	}

	interface Async {
	
		/**
		 * The already completed operation.
		 */
		static Async SYNC = new Async() {
			@Override
			public void await(Runnable r) {
				r.run();
			}

			@Override
			public Async await(Provider<Async> next) {
				return next.get();
			}
		
			@Override
			public Async join(Async other) {
				return other;
			}
		};
	
		/**
		 * Executes the given task after this operation has completed.
		 */
		default void await(Runnable r) {
			await(() -> {
				r.run();
				return SYNC;
			});
		}

		Async await(Provider<Async> next);
	
		default Async join(Async other) {
			return r -> other.await(() -> this.await(r));
		}
	
		static <T> AsyncConsumer<T> whenAccepted() {
			return whenAccepted(t -> {
				// Ignore.
			});
		}

		static <T> AsyncConsumer<T> whenAccepted(Consumer<T> operation) {
			return new AsyncConsumer<>() {
				private boolean _done;
		
				private Provider<Async> _next;
		
				@Override
				public void accept(T t) {
					assert !_done;

					operation.accept(t);

					_done = true;
					if (_next != null) {
						_next.get();
					}
				}
		
				@Override
				public void await(Runnable r) {
					if (_done) {
						r.run();
					} else {
						AsyncConsumer.super.await(r);
					}
				}

				@Override
				public Async await(Provider<Async> next) {
					if (_done) {
						return next.get();
					} else {
						if (_next == null) {
							_next = next;
						} else {
							Provider<Async> first = _next;
							_next = () -> first.get().join(next.get());
						}
						return this;
					}
				}
			};
		}

		interface AsyncConsumer<T> extends Async, Consumer<T> {
			// Pure sum interface.
		}
	}

}
