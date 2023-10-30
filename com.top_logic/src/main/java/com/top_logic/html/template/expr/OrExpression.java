/*
 * SPDX-FileCopyrightText: 2022 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.html.template.expr;

import com.top_logic.html.template.TemplateExpression;
import com.top_logic.layout.DisplayContext;
import com.top_logic.layout.template.WithProperties;

/**
 * Boolean {@link TemplateExpression} combining the result of tow other {@link TemplateExpression}s
 * with <code>or</code>.
 */
public class OrExpression implements TemplateExpression {

	private TemplateExpression _leftExpression;

	private TemplateExpression _rightExpression;

	/**
	 * Creates a {@link OrExpression}.
	 */
	public OrExpression(TemplateExpression leftExpression, TemplateExpression rightExpression) {
		_leftExpression = leftExpression;
		_rightExpression = rightExpression;
	}

	@Override
	public Object eval(DisplayContext context, WithProperties properties) {
		// Note: Allow expressions such as "{attr} || '---'" to produce a string constant, if a
		// value is null.
		Object leftValue = _leftExpression.eval(context, properties);
		return !TestExpression.isTrue(leftValue) ? _rightExpression.eval(context, properties) : leftValue;
	}

}
