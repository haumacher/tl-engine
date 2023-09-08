/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.search.expr.config.operations;

import static com.top_logic.model.search.expr.SearchExpressionFactory.*;

import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.model.search.expr.SearchExpression;
import com.top_logic.model.search.expr.SearchExpressionFactory;
import com.top_logic.model.search.expr.Singleton;
import com.top_logic.model.search.expr.config.dom.Expr;

/**
 * {@link MethodBuilder} creating {@link Singleton} expressions.
 * 
 * @see SearchExpressionFactory#singleton(SearchExpression)
 *
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class SingletonBuilder extends NoArgMethodBuilder<Singleton> {

	/**
	 * Creates a {@link SingletonBuilder}.
	 */
	public SingletonBuilder(InstantiationContext context, Config<?> config) {
		super(context, config);
	}

	@Override
	protected Singleton internalBuild(Expr expr, SearchExpression self) {
		return singleton(self);
	}

}
