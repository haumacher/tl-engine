/*
 * SPDX-FileCopyrightText: 2023 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.demo.model.types.wrap;

/**
 * Basic interface for {@link #PX_TYPE} business objects.
 * 
 * @author Automatically generated by {@link com.top_logic.element.model.generate.InterfaceGenerator}
 */
public interface PXBase extends com.top_logic.demo.model.types.P {

	/**
	 * Name of type <code>PX</code>
	 */
	String PX_TYPE = "PX";

	/**
	 * Part <code>alternativeProtected</code> of <code>PX</code>
	 * 
	 * <p>
	 * Declared as <code>tl.core:Boolean</code> in configuration.
	 * </p>
	 */
	String ALTERNATIVE_PROTECTED_ATTR = "alternativeProtected";

	/**
	 * Getter for part {@link #ALTERNATIVE_PROTECTED_ATTR}.
	 */
	default boolean getAlternativeProtected() {
		return (Boolean) tValueByName(ALTERNATIVE_PROTECTED_ATTR);
	}

	/**
	 * Setter for part {@link #ALTERNATIVE_PROTECTED_ATTR}.
	 */
	default void setAlternativeProtected(boolean newValue) {
		tUpdateByName(ALTERNATIVE_PROTECTED_ATTR, newValue);
	}

}
