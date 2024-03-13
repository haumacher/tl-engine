/*
 * SPDX-FileCopyrightText: 2024 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.demo.model.types.wrap;

/**
 * Basic interface for {@link #PP_TYPE} business objects.
 * 
 * @author Automatically generated by {@link com.top_logic.element.model.generate.InterfaceGenerator}
 */
public interface PPBase extends com.top_logic.demo.model.types.P {

	/**
	 * Name of type <code>PP</code>
	 */
	String PP_TYPE = "PP";

	/**
	 * Part <code>moreProtected</code> of <code>PP</code>
	 * 
	 * <p>
	 * Declared as <code>tl.core:Boolean</code> in configuration.
	 * </p>
	 */
	String MORE_PROTECTED_ATTR = "moreProtected";

	/**
	 * Getter for part {@link #MORE_PROTECTED_ATTR}.
	 */
	default boolean getMoreProtected() {
		return (Boolean) tValueByName(MORE_PROTECTED_ATTR);
	}

	/**
	 * Setter for part {@link #MORE_PROTECTED_ATTR}.
	 */
	default void setMoreProtected(boolean newValue) {
		tUpdateByName(MORE_PROTECTED_ATTR, newValue);
	}

}
