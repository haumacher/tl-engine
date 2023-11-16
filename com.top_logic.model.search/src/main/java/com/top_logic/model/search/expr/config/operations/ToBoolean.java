/*
 * SPDX-FileCopyrightText: 2020 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.search.expr.config.operations;

import java.util.List;

import com.top_logic.basic.config.ConfigurationException;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.element.meta.TypeSpec;
import com.top_logic.model.TLType;
import com.top_logic.model.search.expr.GenericMethod;
import com.top_logic.model.search.expr.SearchExpression;
import com.top_logic.model.search.expr.SimpleGenericMethod;
import com.top_logic.model.search.expr.config.dom.Expr;
import com.top_logic.model.util.TLModelUtil;

/**
 * Explicitly evaluating an expression in a boolean context converting the value to {@link Boolean}
 * type.
 *
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class ToBoolean extends SimpleGenericMethod {

	/**
	 * Creates a {@link ToBoolean}.
	 */
	protected ToBoolean(String name, SearchExpression[] arguments) {
		super(name, arguments);
	}

	@Override
	public Object eval(Object[] arguments) {
		return asBoolean(arguments[0]);
	}

	@Override
	public GenericMethod copy(SearchExpression[] arguments) {
		return new ToBoolean(getName(), arguments);
	}

	@Override
	public TLType getType(List<TLType> argumentTypes) {
		return TLModelUtil.findType(TypeSpec.BOOLEAN_TYPE);
	}

	/**
	 * {@link AbstractSimpleMethodBuilder} creating {@link ToList}.
	 */
	public static final class Builder extends AbstractSimpleMethodBuilder<ToBoolean> {

		/**
		 * Creates a {@link Builder}.
		 */
		public Builder(InstantiationContext context, Config<?> config) {
			super(context, config);
		}

		@Override
		public ToBoolean build(Expr expr, SearchExpression[] args)
				throws ConfigurationException {
			checkSingleArg(expr, args);
			return new ToBoolean(getConfig().getName(), args);
		}

	}

}
