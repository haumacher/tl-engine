/*
 * SPDX-FileCopyrightText: 2022 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package test.com.top_logic.model.search.model.testJavaBinding.impl;

/**
 * Basic interface for {@link #REFERENCES_DERIVED_TYPE} business objects.
 * 
 * @author Automatically generated by {@link com.top_logic.element.model.generate.InterfaceGenerator}
 */
public interface ReferencesDerivedBase extends test.com.top_logic.model.search.model.testJavaBinding.Common {

	/**
	 * Name of type <code>ReferencesDerived</code>
	 */
	String REFERENCES_DERIVED_TYPE = "ReferencesDerived";

	/**
	 * Part <code>in</code> of <code>ReferencesDerived</code>
	 * 
	 * <p>
	 * Declared as <code>TestJavaBinding:A</code> in configuration.
	 * </p>
	 */
	String IN_ATTR = "in";

	/**
	 * Part <code>ref</code> of <code>ReferencesDerived</code>
	 * 
	 * <p>
	 * Declared as <code>TestJavaBinding:Primitives</code> in configuration.
	 * </p>
	 */
	String REF_ATTR = "ref";

	/**
	 * Part <code>refBag</code> of <code>ReferencesDerived</code>
	 * 
	 * <p>
	 * Declared as <code>TestJavaBinding:Primitives</code> in configuration.
	 * </p>
	 */
	String REF_BAG_ATTR = "refBag";

	/**
	 * Part <code>refMandatory</code> of <code>ReferencesDerived</code>
	 * 
	 * <p>
	 * Declared as <code>TestJavaBinding:Primitives</code> in configuration.
	 * </p>
	 */
	String REF_MANDATORY_ATTR = "refMandatory";

	/**
	 * Part <code>refMultiple</code> of <code>ReferencesDerived</code>
	 * 
	 * <p>
	 * Declared as <code>TestJavaBinding:Primitives</code> in configuration.
	 * </p>
	 */
	String REF_MULTIPLE_ATTR = "refMultiple";

	/**
	 * Part <code>refMultipleMandatory</code> of <code>ReferencesDerived</code>
	 * 
	 * <p>
	 * Declared as <code>TestJavaBinding:Primitives</code> in configuration.
	 * </p>
	 */
	String REF_MULTIPLE_MANDATORY_ATTR = "refMultipleMandatory";

	/**
	 * Part <code>refOrdered</code> of <code>ReferencesDerived</code>
	 * 
	 * <p>
	 * Declared as <code>TestJavaBinding:Primitives</code> in configuration.
	 * </p>
	 */
	String REF_ORDERED_ATTR = "refOrdered";

	/**
	 * Part <code>refOrderedBag</code> of <code>ReferencesDerived</code>
	 * 
	 * <p>
	 * Declared as <code>TestJavaBinding:Primitives</code> in configuration.
	 * </p>
	 */
	String REF_ORDERED_BAG_ATTR = "refOrderedBag";

	/**
	 * Getter for part {@link #IN_ATTR}.
	 */
	@SuppressWarnings("unchecked")
	default java.util.Set<? extends test.com.top_logic.model.search.model.testJavaBinding.A> getIn() {
		return (java.util.Set<? extends test.com.top_logic.model.search.model.testJavaBinding.A>) tValueByName(IN_ATTR);
	}

	/**
	 * Live view of the {@link #IN_ATTR} part.
	 * <p>
	 * Changes to this {@link java.util.Collection} change directly the attribute value.
	 * The caller has to take care of the transaction handling.
	 * </p>
	 */
	default java.util.Set<test.com.top_logic.model.search.model.testJavaBinding.A> getInModifiable() {
		com.top_logic.model.TLStructuredTypePart attribute = tType().getPart(IN_ATTR);
		@SuppressWarnings("unchecked")
		java.util.Set<test.com.top_logic.model.search.model.testJavaBinding.A> result = (java.util.Set<test.com.top_logic.model.search.model.testJavaBinding.A>) com.top_logic.element.meta.kbbased.WrapperMetaAttributeUtil.getLiveCollection(this, attribute);
		return result;
	}

	/**
	 * Setter for part {@link #IN_ATTR}.
	 */
	default void setIn(java.util.Set<test.com.top_logic.model.search.model.testJavaBinding.A> newValue) {
		tUpdateByName(IN_ATTR, newValue);
	}

	/**
	 * Adds a value to the {@link #IN_ATTR} reference.
	 */
	default void addIn(test.com.top_logic.model.search.model.testJavaBinding.A newValue) {
		tAddByName(IN_ATTR, newValue);
	}

	/**
	 * Removes the given value from the {@link #IN_ATTR} reference.
	 */
	default void removeIn(test.com.top_logic.model.search.model.testJavaBinding.A oldValue) {
		tRemoveByName(IN_ATTR, oldValue);
	}

	/**
	 * Getter for part {@link #REF_ATTR}.
	 */
	default test.com.top_logic.model.search.model.testJavaBinding.Primitives getRef() {
		return (test.com.top_logic.model.search.model.testJavaBinding.Primitives) tValueByName(REF_ATTR);
	}

	/**
	 * Getter for part {@link #REF_BAG_ATTR}.
	 */
	@SuppressWarnings("unchecked")
	default java.util.Collection<? extends test.com.top_logic.model.search.model.testJavaBinding.Primitives> getRefBag() {
		return (java.util.Collection<? extends test.com.top_logic.model.search.model.testJavaBinding.Primitives>) tValueByName(REF_BAG_ATTR);
	}

	/**
	 * Getter for part {@link #REF_MANDATORY_ATTR}.
	 */
	default test.com.top_logic.model.search.model.testJavaBinding.Primitives getRefMandatory() {
		return (test.com.top_logic.model.search.model.testJavaBinding.Primitives) tValueByName(REF_MANDATORY_ATTR);
	}

	/**
	 * Getter for part {@link #REF_MULTIPLE_ATTR}.
	 */
	@SuppressWarnings("unchecked")
	default java.util.Set<? extends test.com.top_logic.model.search.model.testJavaBinding.Primitives> getRefMultiple() {
		return (java.util.Set<? extends test.com.top_logic.model.search.model.testJavaBinding.Primitives>) tValueByName(REF_MULTIPLE_ATTR);
	}

	/**
	 * Getter for part {@link #REF_MULTIPLE_MANDATORY_ATTR}.
	 */
	@SuppressWarnings("unchecked")
	default java.util.Set<? extends test.com.top_logic.model.search.model.testJavaBinding.Primitives> getRefMultipleMandatory() {
		return (java.util.Set<? extends test.com.top_logic.model.search.model.testJavaBinding.Primitives>) tValueByName(REF_MULTIPLE_MANDATORY_ATTR);
	}

	/**
	 * Getter for part {@link #REF_ORDERED_ATTR}.
	 */
	@SuppressWarnings("unchecked")
	default java.util.List<? extends test.com.top_logic.model.search.model.testJavaBinding.Primitives> getRefOrdered() {
		return (java.util.List<? extends test.com.top_logic.model.search.model.testJavaBinding.Primitives>) tValueByName(REF_ORDERED_ATTR);
	}

	/**
	 * Getter for part {@link #REF_ORDERED_BAG_ATTR}.
	 */
	@SuppressWarnings("unchecked")
	default java.util.List<? extends test.com.top_logic.model.search.model.testJavaBinding.Primitives> getRefOrderedBag() {
		return (java.util.List<? extends test.com.top_logic.model.search.model.testJavaBinding.Primitives>) tValueByName(REF_ORDERED_BAG_ATTR);
	}

}
