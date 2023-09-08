/*
 * SPDX-FileCopyrightText: 2020 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.search.expr.config.operations;

import java.util.List;

import com.top_logic.basic.config.ConfigurationException;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.model.TLObject;
import com.top_logic.model.TLType;
import com.top_logic.model.search.expr.GenericMethod;
import com.top_logic.model.search.expr.SearchExpression;
import com.top_logic.model.search.expr.SimpleGenericMethod;
import com.top_logic.model.search.expr.config.dom.Expr;

/**
 * {@link GenericMethod} looking up the {@link TLObject#tContainerReference()} of an object.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class ContainerReference extends SimpleGenericMethod {

	/**
	 * Creates a {@link ContainerReference}.
	 */
	protected ContainerReference(String name, SearchExpression self, SearchExpression[] arguments) {
		super(name, self, arguments);
	}

	@Override
	public GenericMethod copy(SearchExpression self, SearchExpression[] arguments) {
		return new ContainerReference(getName(), self, arguments);
	}

	@Override
	public TLType getType(TLType selfType, List<TLType> argumentTypes) {
		return null;
	}

	@Override
	public Object eval(Object self, Object[] arguments) {
		TLObject object = asTLObject(self);
		if (object != null) {
			return object.tContainerReference();
		} else {
			return null;
		}
	}

	/**
	 * {@link MethodBuilder} creating {@link ContainerReference}.
	 */
	public static final class Builder extends AbstractSimpleMethodBuilder<ContainerReference> {
		/**
		 * Creates a {@link Builder}.
		 */
		public Builder(InstantiationContext context, Config<?> config) {
			super(context, config);
		}

		@Override
		public ContainerReference build(Expr expr, SearchExpression self, SearchExpression[] args)
				throws ConfigurationException {
			checkNoArguments(expr, self, args);
			return new ContainerReference(getConfig().getName(), self, args);
		}
	}
}
