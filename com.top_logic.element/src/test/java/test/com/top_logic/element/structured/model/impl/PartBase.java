/*
 * SPDX-FileCopyrightText: 2024 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package test.com.top_logic.element.structured.model.impl;

/**
 * Basic interface for {@link #PART_TYPE} business objects.
 * 
 * @author Automatically generated by {@link com.top_logic.element.model.generate.InterfaceGenerator}
 */
public interface PartBase extends com.top_logic.model.TLNamed {

	/**
	 * Name of type <code>Part</code>
	 */
	String PART_TYPE = "Part";

	/**
	 * Part <code>name</code> of <code>Part</code>
	 * 
	 * <p>
	 * Declared as <code>tl.core:String</code> in configuration.
	 * </p>
	 */
	String NAME_ATTR = "name";

}
