/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.search.expr.config.operations;

import com.top_logic.basic.config.ConfigurationException;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.model.search.expr.SearchExpression;
import com.top_logic.model.search.expr.config.dom.Expr;

/**
 * {@link MethodBuilder} for methods with a single argument.
 *
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public abstract class TwoArgMethodBuilder<E extends SearchExpression> extends AbstractSimpleMethodBuilder<E> {
	/**
	 * Creates a {@link TwoArgMethodBuilder}.
	 */
	public TwoArgMethodBuilder(InstantiationContext context, Config<?> config) {
		super(context, config);
	}

	@Override
	public E build(Expr expr, SearchExpression self, SearchExpression[] args)
			throws ConfigurationException {
		checkTwoArgs(expr, args);
		return internalBuild(expr, self, args[0], args[1]);
	}

	/**
	 * Implementation of {@link #build(Expr, SearchExpression, SearchExpression[])}
	 */
	protected abstract E internalBuild(Expr expr, SearchExpression self,
			SearchExpression arg1, SearchExpression arg2) throws ConfigurationException;

}
