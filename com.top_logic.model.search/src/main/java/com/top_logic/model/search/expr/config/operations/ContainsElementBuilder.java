/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.search.expr.config.operations;

import static com.top_logic.model.search.expr.SearchExpressionFactory.*;

import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.model.search.expr.ContainsElement;
import com.top_logic.model.search.expr.SearchExpression;
import com.top_logic.model.search.expr.SearchExpressionFactory;
import com.top_logic.model.search.expr.config.dom.Expr;

/**
 * {@link MethodBuilder} creating {@link ContainsElement} expressions.
 * 
 * @see SearchExpressionFactory#containsElement(SearchExpression, SearchExpression)
 *
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class ContainsElementBuilder extends TwoArgsMethodBuilder<ContainsElement> {

	/**
	 * Creates a {@link ContainsElementBuilder}.
	 */
	public ContainsElementBuilder(InstantiationContext context, Config<?> config) {
		super(context, config);
	}

	@Override
	protected ContainsElement internalBuild(Expr expr, SearchExpression arg0, SearchExpression arg1, SearchExpression[] allArgs) {
		return containsElement(arg0, arg1);
	}

}
