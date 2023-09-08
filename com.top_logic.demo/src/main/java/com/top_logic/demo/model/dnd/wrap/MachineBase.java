/*
 * SPDX-FileCopyrightText: 2023 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.demo.model.dnd.wrap;

/**
 * Basic interface for {@link #MACHINE_TYPE} business objects.
 * 
 * @author Automatically generated by {@link com.top_logic.element.model.generate.InterfaceGenerator}
 */
public interface MachineBase extends com.top_logic.demo.model.dnd.NamedPlanElement {

	/**
	 * Name of type <code>Machine</code>
	 */
	String MACHINE_TYPE = "Machine";

	/**
	 * Part <code>location</code> of <code>Machine</code>
	 * 
	 * <p>
	 * Declared as <code>test.dnd:Location</code> in configuration.
	 * </p>
	 */
	String LOCATION_ATTR = "location";

	/**
	 * Getter for part {@link #LOCATION_ATTR}.
	 */
	default com.top_logic.demo.model.dnd.Location getLocation() {
		return (com.top_logic.demo.model.dnd.Location) tValueByName(LOCATION_ATTR);
	}

	/**
	 * Setter for part {@link #LOCATION_ATTR}.
	 */
	default void setLocation(com.top_logic.demo.model.dnd.Location newValue) {
		tUpdateByName(LOCATION_ATTR, newValue);
	}

}
