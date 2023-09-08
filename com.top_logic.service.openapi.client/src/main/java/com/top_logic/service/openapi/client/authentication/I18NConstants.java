/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.service.openapi.client.authentication;

import com.top_logic.basic.util.ResKey;
import com.top_logic.basic.util.ResKey1;
import com.top_logic.basic.util.ResKey3;
import com.top_logic.layout.I18NConstantsBase;

/**
 * I18N of this package.
 * 
 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
 */
@SuppressWarnings("javadoc")
public class I18NConstants extends I18NConstantsBase {

	public static ResKey1 ERROR_NO_API_KEY__AUTHENTICATION_NAME;

	public static ResKey1 ERROR_NO_CLIENT_CREDENTIALS__AUTHENTICATION_NAME;

	public static ResKey1 ERROR_BASIC_AUTH_CREDENTIALS__AUTHENTICATION_NAME;

	public static ResKey1 ERROR_NO_OPEN_ID_CONFIGURATION__AUTHENTICATION_NAME;

	public static ResKey3 ERROR_NO_TOKEN_GENERATED__STATUS_CODE__CODE__DESCRIPTION;

	public static ResKey ERROR_SENDING_TOKEN_REQUEST;

	public static ResKey ERROR_PARSING_TOKEN_RESPONSE;

    static {
        initConstants(I18NConstants.class);
    }

}
