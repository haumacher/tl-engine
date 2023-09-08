/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.form.constraints;

import java.util.Date;

import com.top_logic.basic.DateUtil;

/**
 * Dates are compared exactly in milliseconds.
 * 
 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
 */
public class DateByDateComparator extends DateComparator {

	@Override
	public int compare(Date date1, Date date2) {
		return DateUtil.compareDates(date1, date2);
	}

}
