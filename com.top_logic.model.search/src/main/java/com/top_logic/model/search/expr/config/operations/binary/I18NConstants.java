/*
 * SPDX-FileCopyrightText: 2023 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.search.expr.config.operations.binary;

import com.top_logic.basic.util.ResKey1;
import com.top_logic.basic.util.ResKey2;
import com.top_logic.layout.I18NConstantsBase;

/**
 * Internationalization constants for this package.
 */
public class I18NConstants extends I18NConstantsBase {

	/**
	 * @en Invalid content type "{0}" specified: {1}
	 */
	public static ResKey2 ERROR_INVALID_CONTENT_TYPE_SPECIFIED__VALUE_MSG;

	/**
	 * @en Binary data expected got "{0}".
	 */
	public static ResKey1 ERROR_BINARY_DATA_EXPECTED__ACTUAL;

	/**
	 * @en Conversion failed: {0}
	 */
	public static ResKey1 ERROR_CONVERSION_FAILED__MSG;

	static {
		initConstants(I18NConstants.class);
	}
}
