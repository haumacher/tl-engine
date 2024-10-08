/*
 * SPDX-FileCopyrightText: 2024 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.demo.model.plain.wrap;

/**
 * Basic interface for {@link #DEMO_PLAIN_C_TYPE} business objects.
 * 
 * @author Automatically generated by {@link com.top_logic.element.model.generate.InterfaceGenerator}
 */
public interface DemoPlainCBase extends com.top_logic.demo.model.plain.DemoPlainBC {

	/**
	 * Name of type <code>DemoPlain.C</code>
	 */
	String DEMO_PLAIN_C_TYPE = "DemoPlain.C";

	/**
	 * Part <code>stringInBAndC</code> of <code>DemoPlain.C</code>
	 * 
	 * <p>
	 * Declared as <code>tl.core:String</code> in configuration.
	 * </p>
	 */
	String STRING_IN_B_AND_C_ATTR = "stringInBAndC";

	/**
	 * Getter for part {@link #STRING_IN_B_AND_C_ATTR}.
	 */
	default String getStringInBAndC() {
		return (String) tValueByName(STRING_IN_B_AND_C_ATTR);
	}

	/**
	 * Setter for part {@link #STRING_IN_B_AND_C_ATTR}.
	 */
	default void setStringInBAndC(String newValue) {
		tUpdateByName(STRING_IN_B_AND_C_ATTR, newValue);
	}

}
