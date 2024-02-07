/*
 * SPDX-FileCopyrightText: 2024 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.demo.model.aspect.wrap;

/**
 * Basic interface for {@link #C_DEFAULTS_TYPE} business objects.
 * 
 * @author Automatically generated by {@link com.top_logic.element.model.generate.InterfaceGenerator}
 */
public interface CDefaultsBase extends com.top_logic.demo.model.aspect.C {

	/**
	 * Name of type <code>CDefaults</code>
	 */
	String C_DEFAULTS_TYPE = "CDefaults";

	/**
	 * Part <code>booleanWithDefault</code> of <code>CDefaults</code>
	 * 
	 * <p>
	 * Declared as <code>tl.core:Boolean</code> in configuration.
	 * </p>
	 */
	String BOOLEAN_WITH_DEFAULT_ATTR = "booleanWithDefault";

	/**
	 * Part <code>dateWithDefault</code> of <code>CDefaults</code>
	 * 
	 * <p>
	 * Declared as <code>tl.core:Date</code> in configuration.
	 * </p>
	 */
	String DATE_WITH_DEFAULT_ATTR = "dateWithDefault";

	/**
	 * Part <code>longWithDefault</code> of <code>CDefaults</code>
	 * 
	 * <p>
	 * Declared as <code>tl.core:Long</code> in configuration.
	 * </p>
	 */
	String LONG_WITH_DEFAULT_ATTR = "longWithDefault";

	/**
	 * Getter for part {@link #BOOLEAN_WITH_DEFAULT_ATTR}.
	 */
	default boolean getBooleanWithDefault() {
		return (Boolean) tValueByName(BOOLEAN_WITH_DEFAULT_ATTR);
	}

	/**
	 * Setter for part {@link #BOOLEAN_WITH_DEFAULT_ATTR}.
	 */
	default void setBooleanWithDefault(boolean newValue) {
		tUpdateByName(BOOLEAN_WITH_DEFAULT_ATTR, newValue);
	}

	/**
	 * Getter for part {@link #DATE_WITH_DEFAULT_ATTR}.
	 */
	default java.util.Date getDateWithDefault() {
		return (java.util.Date) tValueByName(DATE_WITH_DEFAULT_ATTR);
	}

	/**
	 * Setter for part {@link #DATE_WITH_DEFAULT_ATTR}.
	 */
	default void setDateWithDefault(java.util.Date newValue) {
		tUpdateByName(DATE_WITH_DEFAULT_ATTR, newValue);
	}

	/**
	 * Getter for part {@link #LONG_WITH_DEFAULT_ATTR}.
	 */
	default Long getLongWithDefault() {
		return (Long) tValueByName(LONG_WITH_DEFAULT_ATTR);
	}

	/**
	 * Setter for part {@link #LONG_WITH_DEFAULT_ATTR}.
	 */
	default void setLongWithDefault(Long newValue) {
		tUpdateByName(LONG_WITH_DEFAULT_ATTR, newValue);
	}

}
