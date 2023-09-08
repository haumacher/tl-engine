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
 * Combines the {@link SearchExpression} with "not none of the expressions must match".
 * 
 * @author <a href="mailto:jst@top-logic.com">Jan Stolzenburg</a>
 */
public final class NotNoneSearchExpressionCombinator extends AbstractSearchExpressionCombinator {

	/**
	 * The instance of the {@link NotNoneSearchExpressionCombinator}.
	 */
	public static final NotNoneSearchExpressionCombinator INSTANCE = new NotNoneSearchExpressionCombinator();

	@Override
	public SearchExpression combineInternal(List<SearchExpression> expressions) {
		assert !expressions.isEmpty() : "The superclass did not handle the empty list case as promised by the JavaDoc.";
		SearchExpression combinedExpression = literal(false);
		for (SearchExpression expression : expressions) {
			combinedExpression = or(combinedExpression, expression);
		}
		return combinedExpression;
	}

}
