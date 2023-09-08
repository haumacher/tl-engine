/*
 * SPDX-FileCopyrightText: 2016 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.table.filter;

/**
 * {@link ComparableTableFilterProvider}, that provides a mandatory {@link ComparableFilter}, with
 * suitable comparison options for float values.
 * 
 * @author <a href="mailto:sts@top-logic.com">Stefan Steinert</a>
 */
public class MandatoryFloatValuesTableFilterProvider extends NumberTableFilterProvider {

	/** Instance of {@link MandatoryFloatValuesTableFilterProvider} */
	public static final MandatoryFloatValuesTableFilterProvider INSTANCE =
		new MandatoryFloatValuesTableFilterProvider();

	private MandatoryFloatValuesTableFilterProvider() {
		super(true, FloatValueOperatorsProvider.INSTANCE);
	}
}
