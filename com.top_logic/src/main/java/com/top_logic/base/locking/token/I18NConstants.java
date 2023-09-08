/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.base.locking.token;

import com.top_logic.basic.util.ResKey;
import com.top_logic.basic.util.ResKey1;
import com.top_logic.basic.util.ResKey3;
import com.top_logic.layout.I18NConstantsBase;

/**
 * Internationalization constants for this package.
 */
@SuppressWarnings("javadoc")
public class I18NConstants extends I18NConstantsBase {

	public static ResKey ERROR_LOCK_CONFLICT;

	public static ResKey1 ERROR_DATABASE_ACCESS__PROBLEM;

	public static ResKey1 ERROR_REFETCH__PROBLEM;

	public static ResKey3 ERROR_LOCK_CONFLICT__OWNER_TIMEOUT_NODE;

	static {
		initConstants(I18NConstants.class);
	}
}
