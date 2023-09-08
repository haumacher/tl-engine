/*
 * SPDX-FileCopyrightText: 2022 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.bpe.bpml.model.impl;

/**
 * Basic interface for {@link #EXTERNALIZED_TYPE} business objects.
 * 
 * @author Automatically generated by {@link com.top_logic.element.model.generate.InterfaceGenerator}
 */
public interface ExternalizedBase extends com.top_logic.model.TLObject {

	/**
	 * Name of type <code>Externalized</code>
	 */
	String EXTERNALIZED_TYPE = "Externalized";

	/**
	 * Part <code>extId</code> of <code>Externalized</code>
	 * 
	 * <p>
	 * Declared as <code>tl.core:String</code> in configuration.
	 * </p>
	 */
	String EXT_ID_ATTR = "extId";

	/**
	 * Getter for part {@link #EXT_ID_ATTR}.
	 */
	default String getExtId() {
		return (String) tValueByName(EXT_ID_ATTR);
	}

	/**
	 * Setter for part {@link #EXT_ID_ATTR}.
	 */
	default void setExtId(String newValue) {
		tUpdateByName(EXT_ID_ATTR, newValue);
	}

}
