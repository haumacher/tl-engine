/*
 * SPDX-FileCopyrightText: 2023 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.search.expr.config.operations.revision;

import java.util.List;

import com.top_logic.basic.config.ConfigurationException;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.knowledge.service.Revision;
import com.top_logic.model.TLType;
import com.top_logic.model.core.TlCoreFactory;
import com.top_logic.model.search.expr.GenericMethod;
import com.top_logic.model.search.expr.SearchExpression;
import com.top_logic.model.search.expr.SimpleGenericMethod;
import com.top_logic.model.search.expr.config.dom.Expr;
import com.top_logic.model.search.expr.config.operations.AbstractSimpleMethodBuilder;

/**
 * Generic method determining {@link Revision#CURRENT}.
 * 
 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
 */
public class CurrentRevision extends SimpleGenericMethod {

	/**
	 * Creates a new {@link CurrentRevision}.
	 */
	protected CurrentRevision(String name, SearchExpression self, SearchExpression[] arguments) {
		super(name, self, arguments);
	}

	@Override
	public GenericMethod copy(SearchExpression self, SearchExpression[] arguments) {
		return new CurrentRevision(getName(), self, arguments);
	}

	@Override
	public TLType getType(TLType selfType, List<TLType> argumentTypes) {
		return TlCoreFactory.getRevisionType();
	}

	@Override
	public Object eval(Object self, Object[] arguments) {
		return Revision.CURRENT;
	}

	/**
	 * {@link AbstractSimpleMethodBuilder} creating {@link CurrentRevision}.
	 */
	public static final class Builder extends AbstractSimpleMethodBuilder<CurrentRevision> {

		/**
		 * Creates a {@link Builder}.
		 */
		public Builder(InstantiationContext context, Config<?> config) {
			super(context, config);
		}

		@Override
		public CurrentRevision build(Expr expr, SearchExpression self, SearchExpression[] args)
				throws ConfigurationException {
			checkNoArguments(expr, args);
			return new CurrentRevision(getConfig().getName(), self, args);
		}

	}

}

