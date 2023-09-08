/*
 * SPDX-FileCopyrightText: 2020 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.search.expr.config.operations.revision;

import java.util.Date;
import java.util.List;

import com.top_logic.basic.config.ConfigurationException;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.knowledge.service.HistoryUtils;
import com.top_logic.knowledge.service.Revision;
import com.top_logic.model.TLType;
import com.top_logic.model.core.TlCoreFactory;
import com.top_logic.model.search.expr.EvalContext;
import com.top_logic.model.search.expr.GenericMethod;
import com.top_logic.model.search.expr.SearchExpression;
import com.top_logic.model.search.expr.config.dom.Expr;
import com.top_logic.model.search.expr.config.operations.AbstractSimpleMethodBuilder;

/**
 * Generic method resolving the newest revision not later than a given {@link Date}.
 * 
 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
 */
public class RevisionAt extends GenericMethod {

	/**
	 * Creates a new {@link RevisionAt}.
	 */
	protected RevisionAt(String name, SearchExpression self, SearchExpression[] arguments) {
		super(name, self, arguments);
	}

	@Override
	public GenericMethod copy(SearchExpression self, SearchExpression[] arguments) {
		return new RevisionAt(getName(), self, arguments);
	}

	@Override
	public TLType getType(TLType selfType, List<TLType> argumentTypes) {
		return TlCoreFactory.getRevisionType();
	}

	@Override
	protected Object eval(Object self, Object[] arguments, EvalContext definitions) {
		if (self == null) {
			return null;
		}
		Date date = asDate(self);
		Revision requestedRevision = HistoryUtils.getRevisionAt(date.getTime());
		Revision sessionRevision = HistoryUtils.getSessionRevision();
		if (requestedRevision.compareTo(sessionRevision) > 0) {
			// Do not allow access to revision later than the session.
			return sessionRevision;
		}

		return requestedRevision;
	}

	/**
	 * {@link AbstractSimpleMethodBuilder} creating {@link RevisionAt}.
	 */
	public static final class Builder extends AbstractSimpleMethodBuilder<RevisionAt> {

		/**
		 * Creates a {@link Builder}.
		 */
		public Builder(InstantiationContext context, Config<?> config) {
			super(context, config);
		}

		@Override
		public RevisionAt build(Expr expr, SearchExpression self, SearchExpression[] args)
				throws ConfigurationException {
			checkNoArguments(expr, args);
			return new RevisionAt(getConfig().getName(), self, args);
		}

	}

}

