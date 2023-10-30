/*
 * SPDX-FileCopyrightText: 2022 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.html.template.expr;

import com.top_logic.html.template.TemplateExpression;

/**
 * {@link NumericExpression} for an addition.
 */
public class AddExpression extends NumericExpression {

	/**
	 * Creates a {@link AddExpression}.
	 */
	public AddExpression(TemplateExpression left, TemplateExpression right) {
		super(left, right);
	}

	@Override
	protected Object compute(Object leftValue, Object rightValue) {
		if (leftValue == null) {
			return rightValue;
		} else if (rightValue == null) {
			return leftValue;
		} else if (leftValue instanceof CharSequence || rightValue instanceof CharSequence) {
			return leftValue.toString() + rightValue.toString();
		} else {
			return super.compute(leftValue, rightValue);
		}
	}

	@Override
	protected Object computeDouble(double leftNum, double rightNum) {
		return leftNum + rightNum;
	}

	@Override
	protected Object computeLong(long leftNum, long rightNum) {
		return leftNum + rightNum;
	}


}
