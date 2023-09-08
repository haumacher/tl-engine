/*
 * SPDX-FileCopyrightText: 2016 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.search.ui.model.combinator;

import static com.top_logic.model.search.expr.SearchExpressionFactory.*;

import java.util.List;

import com.top_logic.model.search.expr.SearchExpression;

/**
 * Combines the {@link SearchExpression} with "none of the expressions must match".
 * 
 * @author <a href="mailto:jst@top-logic.com">Jan Stolzenburg</a>
 */
public final class NoneSearchExpressionCombinator extends AbstractSearchExpressionCombinator {

	/**
	 * The instance of the {@link NoneSearchExpressionCombinator}.
	 */
	public static final NoneSearchExpressionCombinator INSTANCE = new NoneSearchExpressionCombinator();

	@Override
	public SearchExpression combineInternal(List<SearchExpression> expressions) {
		SearchExpression combinedExpression = literal(true);
		for (SearchExpression expression : expressions) {
			combinedExpression = and(combinedExpression, not(expression));
		}
		return combinedExpression;
	}

}
