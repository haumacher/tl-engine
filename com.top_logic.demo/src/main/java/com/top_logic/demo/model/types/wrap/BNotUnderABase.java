/*
 * SPDX-FileCopyrightText: 2024 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.demo.model.types.wrap;

/**
 * Basic interface for {@link #B_NOT_UNDER_A_TYPE} business objects.
 * 
 * @author Automatically generated by {@link com.top_logic.element.model.generate.InterfaceGenerator}
 */
public interface BNotUnderABase extends com.top_logic.demo.model.types.DemoTypesB, com.top_logic.demo.model.types.RootChild, com.top_logic.demo.model.types.DemoTypesContainer {

	/**
	 * Name of type <code>BNotUnderA</code>
	 */
	String B_NOT_UNDER_A_TYPE = "BNotUnderA";

	/**
	 * Getter for part {@link #CHILDREN_ATTR}.
	 */
	@Override
	@SuppressWarnings("unchecked")
	default java.util.List<? extends com.top_logic.demo.model.types.BNotUnderAChild> getChildren() {
		return (java.util.List<? extends com.top_logic.demo.model.types.BNotUnderAChild>) tValueByName(CHILDREN_ATTR);
	}

}
