/*
 * SPDX-FileCopyrightText: 2016 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.reporting.report.view.component;

import com.top_logic.basic.util.ResKey;
import com.top_logic.layout.I18NConstantsBase;
import com.top_logic.layout.ResPrefix;

/**
 * Internationalization constants for this package.
 *
 * @see ResPrefix
 * @see ResKey
 */
@SuppressWarnings("javadoc")
public class I18NConstants extends I18NConstantsBase {

	public static ResPrefix TL = legacyPrefix("tl.");

	public static ResKey DATE_LABEL = legacyKey("reporting.chart.export.date.label");

	public static ResKey TITLE_LABEL = legacyKey("reporting.chart.export.title.label");

	public static ResKey USER_LABEL = legacyKey("reporting.chart.export.user.label");

	static {
		initConstants(I18NConstants.class);
	}
}
