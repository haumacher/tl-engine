/*
 * SPDX-FileCopyrightText: 2022 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.service.openapi.client.layout;

import com.top_logic.basic.util.ResKey1;
import com.top_logic.basic.util.ResKey2;
import com.top_logic.layout.I18NConstantsBase;

/**
 * {@link I18NConstants} for this package.
 * 
 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
 */
public class I18NConstants extends I18NConstantsBase {

	/** @en The parameter type {1} in parameter {0} is not supported. */
	public static ResKey2 UNSUPPORTED_PARAMETER_TYPE__PARAMETER__TYPE;

	/** @en Object schema expected for ''multipart/form-data'' body in method {0}. */
	public static ResKey1 UNEXPECTED_SCHEMA_FOR_MULTIPART_BODY__METHOD;

	static {
		initConstants(I18NConstants.class);
	}
}
