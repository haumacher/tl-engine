/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.search.expr.config.operations;

import static com.top_logic.model.search.expr.SearchExpressionFactory.*;

import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.model.search.expr.IsEmpty;
import com.top_logic.model.search.expr.SearchExpression;
import com.top_logic.model.search.expr.SearchExpressionFactory;
import com.top_logic.model.search.expr.config.dom.Expr;

/**
 * {@link MethodBuilder} creating {@link IsEmpty} expressions.
 * 
 * @see SearchExpressionFactory#isEmpty(SearchExpression)
 *
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class IsEmptyBuilder extends SingleArgMethodBuilder<IsEmpty> {

	/**
	 * Creates a {@link IsEmptyBuilder}.
	 */
	public IsEmptyBuilder(InstantiationContext context, Config<?> config) {
		super(context, config);
	}

	@Override
	protected IsEmpty internalBuild(Expr expr, SearchExpression argument, SearchExpression[] allArgs) {
		return isEmpty(argument);
	}

}
