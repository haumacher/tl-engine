/*
 * SPDX-FileCopyrightText: 2023 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.demo.model.types.wrap;

/**
 * Basic interface for {@link #P_TYPE} business objects.
 * 
 * @author Automatically generated by {@link com.top_logic.element.model.generate.InterfaceGenerator}
 */
public interface PBase extends com.top_logic.demo.model.types.DemoTypesAll, com.top_logic.demo.model.types.BChild, com.top_logic.demo.model.types.BNotUnderAChild {

	/**
	 * Name of type <code>P</code>
	 */
	String P_TYPE = "P";

	/**
	 * Part <code>protected</code> of <code>P</code>
	 * 
	 * <p>
	 * Declared as <code>tl.core:Boolean</code> in configuration.
	 * </p>
	 */
	String PROTECTED_ATTR = "protected";

	/**
	 * Getter for part {@link #PROTECTED_ATTR}.
	 */
	default boolean getProtected() {
		return (Boolean) tValueByName(PROTECTED_ATTR);
	}

	/**
	 * Setter for part {@link #PROTECTED_ATTR}.
	 */
	default void setProtected(boolean newValue) {
		tUpdateByName(PROTECTED_ATTR, newValue);
	}

}
