/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.search.expr.config.operations;

import static com.top_logic.model.search.expr.SearchExpressionFactory.*;

import com.top_logic.basic.config.ConfigurationException;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.model.search.expr.All;
import com.top_logic.model.search.expr.DynamicAll;
import com.top_logic.model.search.expr.Literal;
import com.top_logic.model.search.expr.SearchExpression;
import com.top_logic.model.search.expr.SearchExpressionFactory;
import com.top_logic.model.search.expr.config.dom.Expr;

/**
 * {@link MethodBuilder} creating {@link All} expressions.
 * 
 * @see SearchExpressionFactory#all(com.top_logic.model.TLStructuredType)
 *
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class AllBuilder extends SingleArgMethodBuilder<SearchExpression> {

	/**
	 * Creates a {@link AllBuilder}.
	 */
	public AllBuilder(InstantiationContext context, Config<?> config) {
		super(context, config);
	}

	@Override
	protected SearchExpression internalBuild(Expr expr, SearchExpression argument, SearchExpression[] allArgs)
			throws ConfigurationException {
		if (argument instanceof Literal) {
			return all(resolveStructuredType(expr, argument));
		} else {
			return new DynamicAll(getConfig().getName(), allArgs);
		}
	}

}
