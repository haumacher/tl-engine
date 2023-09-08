/*
 * SPDX-FileCopyrightText: 2020 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.element.layout.formeditor.builder;

import java.util.Map;

import com.top_logic.knowledge.wrap.WrapperHistoryUtils;
import com.top_logic.model.TLObject;
import com.top_logic.model.TLStructuredType;
import com.top_logic.model.TLType;
import com.top_logic.model.form.definition.FormDefinition;
import com.top_logic.model.util.TLModelUtil;

/**
 * {@link FormDefinition} with it's defining {@link TLStructuredType}.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class TypedForm {

	private TLStructuredType _displayedType;

	private final TLStructuredType _formType;

	private final FormDefinition _formDefinition;

	private boolean _layoutSpecific;

	/**
	 * Creates a {@link TypedForm} for a type without form definition.
	 * 
	 * @param displayedType
	 *        The type to display in a form.
	 * @param formType
	 *        The type defining the form. May be <code>null</code>, if the displayed type has no
	 *        form-defining generalization.
	 * @param formDefinition
	 *        The {@link FormDefinition} to use for display.
	 * @param layoutSpecific
	 *        See {@link #isLayoutSpecific()}.
	 */
	public TypedForm(TLStructuredType displayedType, TLStructuredType formType, FormDefinition formDefinition, boolean layoutSpecific) {
		_displayedType = displayedType;
		_formType = formType;
		_formDefinition = formDefinition;
		_layoutSpecific = layoutSpecific;
	}

	/**
	 * The type describing the object being displayed or created in a form.
	 * 
	 * @see #getFormType()
	 */
	public TLStructuredType getDisplayedType() {
		return _displayedType;
	}

	/**
	 * The type that defines the {@link #getFormDefinition()}.
	 * 
	 * <p>
	 * If there is no type that defines the {@link #getFormDefinition()}, the result is
	 * <code>null</code>. In that case, the "other attributes" group of the {@link FormDefinition}
	 * displays all attributes that are not otherwise mentioned in the {@link FormDefinition}. If
	 * there is a form-defining type, attributes of this type are not displayed by the "other
	 * attributes" group.
	 * </p>
	 * 
	 * @see #getDisplayedType()
	 */
	public TLStructuredType getFormType() {
		return _formType;
	}

	/**
	 * The {@link FormDefinition} to display an object of {@link #getFormType()}.
	 */
	public FormDefinition getFormDefinition() {
		return _formDefinition;
	}

	/**
	 * Whether the form definition is a specialized version of a specific view.
	 */
	public boolean isLayoutSpecific() {
		return _layoutSpecific;
	}

	/**
	 * Looks up a form for displaying the given object.
	 * 
	 * @param specificFormDefinitions
	 *        Optional {@link FormDefinition} to use (defined by the context). If
	 *        non-<code>null</code>, this {@link FormDefinition} overrides a {@link FormDefinition}
	 *        defined by the object's type.
	 * @param object
	 *        The object to display.
	 * 
	 * @see #lookup(TLStructuredType)
	 */
	public static TypedForm lookup(Map<TLType, FormDefinition> specificFormDefinitions, TLObject object) {
		TLStructuredType type = getType(object);
		if (type == null) {
			return new TypedForm(null, null, null, false);
		} else {
			return lookup(specificFormDefinitions, type);
		}
	}

	/**
	 * Looks up a form for displaying the given type.
	 * 
	 * @param specificFormDefinitions
	 *        Optional {@link FormDefinition} to use (defined by the context). This
	 *        {@link FormDefinition} overrides a {@link FormDefinition} defined by the type.
	 * @param type
	 *        The type to look up a form.
	 * 
	 * @see #lookup(TLStructuredType)
	 */
	public static TypedForm lookup(Map<TLType, FormDefinition> specificFormDefinitions, TLStructuredType type) {
		TypedForm specificForm = findForm(specificFormDefinitions, type);
		if (specificForm != null) {
			return specificForm;
		} else {
			return lookup(type);
		}
	}

	/**
	 * Finds the {@link FormDefinition} for the given {@link TLStructuredType type} in the given
	 * definitions. When no definition can be found for the given type, the definition for the first
	 * (inherited) generalisation is returned.
	 * 
	 * @return The most specific {@link FormDefinition} for the given type in the given definitions
	 *         or <code>null</code>, when no such definition can be found.
	 */
	public static TypedForm findForm(Map<TLType, FormDefinition> formDefinitions, TLStructuredType type) {
		if (formDefinitions != null && !formDefinitions.isEmpty()) {
			// Form types are only for the current time. Therefore, a form can only be found, if the
			// current version of the object's type is used for search.
			TLStructuredType formType = WrapperHistoryUtils.getCurrent(type);
			FormDefinition form = null;

			while (formType != null) {
				form = formDefinitions.get(formType);

				if (form != null) {
					return new TypedForm(type, formType, form, true);
				}

				formType = TLModelUtil.getPrimaryGeneralization(formType);
			}
		}
		return null;
	}

	/**
	 * Looks up the {@link FormDefinition} to display objects of the given type.
	 * 
	 * @param displayedType
	 *        The type that describes the displayed object. For that type a form definition is
	 *        searched for.
	 */
	public static TypedForm lookup(TLStructuredType displayedType) {
		TLStructuredType formType = FormDefinitionUtil.findFormDefiningType(displayedType);
		if (formType != null) {
			return annotatedForm(displayedType, formType);
		}

		TLStructuredType current = WrapperHistoryUtils.getCurrent(displayedType);
		if (current != displayedType) {
			formType = FormDefinitionUtil.findFormDefiningType(current);

			if (formType != null) {
				// Use the form defined for the current version of the displayed type, even if there
				// was no form definition for the version of the type that is currently displayed.
				return annotatedForm(displayedType, formType);
			}
		}

		return new TypedForm(displayedType, null, FormDefinitionUtil.createAllPartsFormDefinition(), false);
	}

	private static TypedForm annotatedForm(TLStructuredType displayedType, TLStructuredType formType) {
		return new TypedForm(displayedType, formType, FormDefinitionUtil.getFormAnnotation(formType).getForm(), false);
	}

	private static TLStructuredType getType(TLObject object) {
		return object == null ? null : object.tType();
	}
}