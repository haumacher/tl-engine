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
 * A negation.
 */
public class NegExpression extends UnaryExpression {

	/**
	 * Creates a {@link NegExpression}.
	 */
	public NegExpression(TemplateExpression expr) {
		super(expr);
	}

	@Override
	public Object eval(DisplayContext context, WithProperties properties) {
		Number num = NumericExpression.toNumber(getExpr().eval(context, properties));
		if (num instanceof Integer || num instanceof Long) {
			return Long.valueOf(-num.longValue());
		} else {
			return Double.valueOf(-num.doubleValue());
		}
	}

	@Override
	public <R, A> R visit(Visitor<R, A> v, A arg) {
		return v.visit(this, arg);
	}

}
