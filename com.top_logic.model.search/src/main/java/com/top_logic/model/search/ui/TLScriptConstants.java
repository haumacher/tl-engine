/*
 * SPDX-FileCopyrightText: 2021 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.search.ui;

/**
 * TL-Script specific constants used by the TL-Script auto completion.
 * 
 * @author <a href="mailto:sfo@top-logic.com">sfo</a>
 */
public interface TLScriptConstants {

	/**
	 * Encloses model constants in TL-Script.
	 */
	public static final String MODEL_SCOPE_SEPARATOR = "`";

	/**
	 * Separates the module and type in model constants.
	 */
	public static final String MODEL_TYPE_SEPARATOR = ":";

	/**
	 * Separates type and attribute in model constants.
	 */
	public static final String MODEL_ATTRIBUTE_SEPARATOR = "#";

}
