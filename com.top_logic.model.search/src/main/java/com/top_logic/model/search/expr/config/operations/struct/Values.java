/*
 * SPDX-FileCopyrightText: 2021 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.search.expr.config.operations.struct;

import java.util.List;

import com.top_logic.basic.config.ConfigurationException;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.model.TLType;
import com.top_logic.model.search.expr.GenericMethod;
import com.top_logic.model.search.expr.SearchExpression;
import com.top_logic.model.search.expr.SimpleGenericMethod;
import com.top_logic.model.search.expr.config.dom.Expr;
import com.top_logic.model.search.expr.config.operations.AbstractSimpleMethodBuilder;
import com.top_logic.model.search.expr.config.operations.MethodBuilder;

/**
 * Transforms a {@link StructValue} to its value collection.
 *
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class Values extends SimpleGenericMethod {

	/**
	 * Creates a {@link Values}.
	 */
	protected Values(String name, SearchExpression self, SearchExpression[] arguments) {
		super(name, self, arguments);
	}

	@Override
	public GenericMethod copy(SearchExpression self, SearchExpression[] arguments) {
		return new Values(getName(), self, arguments);
	}

	@Override
	public TLType getType(TLType selfType, List<TLType> argumentTypes) {
		return null;
	}

	@Override
	public Object eval(Object self, Object[] arguments) {
		return asMap(self).values();
	}

	/**
	 * {@link MethodBuilder} creating {@link Values}.
	 */
	public static final class Builder extends AbstractSimpleMethodBuilder<Values> {
		/**
		 * Creates a {@link Builder}.
		 */
		public Builder(InstantiationContext context, Config<?> config) {
			super(context, config);
		}

		@Override
		public Values build(Expr expr, SearchExpression self, SearchExpression[] args)
				throws ConfigurationException {
			checkNoArguments(expr, self, args);
			return new Values(getConfig().getName(), self, args);
		}
	}

}
