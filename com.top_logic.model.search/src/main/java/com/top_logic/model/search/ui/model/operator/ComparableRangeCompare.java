/*
 * SPDX-FileCopyrightText: 2013 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.search.ui.model.operator;

import static com.top_logic.model.search.expr.SearchExpressionFactory.*;

import com.top_logic.basic.UnreachableAssertion;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.basic.config.annotation.Name;
import com.top_logic.layout.form.template.util.ResourceDisplay;
import com.top_logic.layout.form.values.edit.annotation.UseTemplate;
import com.top_logic.model.search.expr.SearchExpression;
import com.top_logic.model.search.ui.model.exec.ExpressionBuilder;

/**
 * {@link Operator} checking whether a {@link Comparable} value is within a certain range.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
@UseTemplate(ResourceDisplay.class)
public interface ComparableRangeCompare extends PrimitiveRangeCompare<ComparableRangeCompare.Impl> {

	/**
	 * Property name of {@link #getKind()}.
	 */
	String KIND = "kind";

	/**
	 * Kind of comparison.
	 */
	@Name(KIND)
	RangeCompareKind getKind();

	/**
	 * {@link Operator.Impl} for {@link ComparableRangeCompare}.
	 */
	class Impl extends PrimitiveRangeCompare.Impl<ComparableRangeCompare> {

		/**
		 * Creates a {@link Impl}.
		 */
		public Impl(InstantiationContext context, ComparableRangeCompare config) {
			super(context, config);
		}

		@Override
		public SearchExpression build(ExpressionBuilder builder, SearchExpression contextExpr) {
			ComparableRangeCompare comparison = getConfig();
			SearchExpression lowerBound = buildExpression(builder, comparison.getLowerBound());
			SearchExpression upperBound = buildExpression(builder, comparison.getUpperBound());
			switch (comparison.getKind()) {
				case IN_RANGE:
					return not(isInRange(contextExpr, lowerBound, upperBound));
				case NOT_IN_RANGE:
					return isInRange(contextExpr, lowerBound, upperBound);
			}
			throw new UnreachableAssertion("Unhandled kind: " + comparison.getKind());
		}
	}
}
