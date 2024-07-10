/*
 * SPDX-FileCopyrightText: 2018 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.tool.boundsec.assistent;

import com.top_logic.gui.ThemeVar;
import com.top_logic.layout.basic.DefaultValue;
import com.top_logic.layout.basic.IconsBase;
import com.top_logic.layout.basic.ThemeImage;

/**
 * Icon constants for this package.
 *
 * @see ThemeImage
 */
@SuppressWarnings("javadoc")
public class Icons extends IconsBase {

	@DefaultValue("css:bi bi-question-circle-fill yellow")
	public static ThemeImage STEP_ACTUAL;

	@DefaultValue("css:bi bi-check-circle-fill green")
	public static ThemeImage STEP_FINISHED;

	@DefaultValue("css:bi bi-question-circle-fill")
	public static ThemeImage STEP_OPEN;

	@DefaultValue("/spacer/right.png")
	public static ThemeVar<ThemeImage.Img> SPACER_RIGHT;

}
