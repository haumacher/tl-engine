/*
 * SPDX-FileCopyrightText: 2008 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.form.control;

import java.io.IOException;

import com.top_logic.basic.xml.TagWriter;
import com.top_logic.layout.DisplayContext;
import com.top_logic.layout.form.FormConstants;
import com.top_logic.layout.form.model.ComplexField;

/**
 * Control which renders a Date and the possibility to open a calendar to change the date.
 */
public class DateInputControl extends TextInputControl {

	/**
	 * Use a custom default value, to fit a regular date into the input field of
	 * the tag (e.g. 10.11.2003 , Aug. 10. 2003).
	 */
	public static final int DEFAULT_COLUMN_SIZE = 10;

	private final OpenCalendarControl _openCalendar;

	/**
	 * Creates a new {@link DateInputControl} with the given model.
	 */
	public DateInputControl(ComplexField model) {
		super(model, OpenCalendarControl.COMMANDS);
		
		setColumns(DEFAULT_COLUMN_SIZE);
		_openCalendar = new OpenCalendarControl(model);
	}
	
	@Override
	protected String getTypeCssClass() {
		return "cDateInput";
	}
		
	@Override
	protected void writeEditableContents(DisplayContext context, TagWriter out) throws IOException {
		super.writeEditableContents(context, out);

		out.beginBeginTag(SPAN);
		out.writeAttribute(CLASS_ATTR, FormConstants.FIXED_RIGHT_CSS_CLASS);
		out.endBeginTag();

		_openCalendar.write(context, out);

		out.endTag(SPAN);
	}

}
