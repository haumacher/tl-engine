/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.search.expr.config.operations;

import static com.top_logic.model.search.expr.SearchExpressionFactory.*;

import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.model.search.expr.Filter;
import com.top_logic.model.search.expr.SearchExpression;
import com.top_logic.model.search.expr.SearchExpressionFactory;
import com.top_logic.model.search.expr.config.dom.Expr;

/**
 * {@link MethodBuilder} creating {@link Filter} expressions.
 * 
 * @see SearchExpressionFactory#filter(SearchExpression, SearchExpression)
 *
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class FilterBuilder extends SingleArgMethodBuilder<Filter> {
	/**
	 * Creates a {@link FilterBuilder}.
	 */
	public FilterBuilder(InstantiationContext context, Config<?> config) {
		super(context, config);
	}

	@Override
	protected Filter internalBuild(Expr expr, SearchExpression self, SearchExpression arg) {
		return filter(self, arg);
	}

}
