/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.search.expr;

import java.text.MessageFormat;
import java.util.List;

import com.top_logic.basic.Logger;
import com.top_logic.basic.config.ConfigurationException;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.knowledge.wrap.person.Person;
import com.top_logic.model.TLType;
import com.top_logic.model.search.expr.config.dom.Expr;
import com.top_logic.model.search.expr.config.operations.AbstractSimpleMethodBuilder;
import com.top_logic.model.search.expr.config.operations.MethodBuilder;
import com.top_logic.util.TLContext;

/**
 * Reports an info message to the application log.
 *
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class Log extends GenericMethod {

	/**
	 * Creates a {@link Log}.
	 */
	protected Log(String name, SearchExpression self, SearchExpression[] arguments) {
		super(name, self, arguments);
	}

	@Override
	public GenericMethod copy(SearchExpression self, SearchExpression[] arguments) {
		return new Log(getName(), self, arguments);
	}

	@Override
	public TLType getType(TLType selfType, List<TLType> argumentTypes) {
		return null;
	}

	@Override
	protected Object eval(Object self, Object[] arguments, EvalContext definitions) {
		String message = asString(self);
		if (arguments.length > 0) {
			message = MessageFormat.format(message, arguments);
		}
		Logger.info("Script (" + whoAmI() + ") reported: " + message, Log.class);
		return null;
	}

	private String whoAmI() {
		TLContext context = TLContext.getContext();
		if (context == null) {
			return "no context";
		}
		Person user = context.getCurrentPersonWrapper();
		if (user != null) {
			return user.getUser().getUserName();
		}
		return context.getContextId();
	}

	/**
	 * {@link MethodBuilder} creating {@link Log}.
	 */
	public static final class Builder extends AbstractSimpleMethodBuilder<Log> {
		/**
		 * Creates a {@link Builder}.
		 */
		public Builder(InstantiationContext context, Config<?> config) {
			super(context, config);
		}

		@Override
		public Log build(Expr expr, SearchExpression self, SearchExpression[] args)
				throws ConfigurationException {
			checkHasTarget(expr, self);
			return new Log(getName(), self, args);
		}
	}
}
