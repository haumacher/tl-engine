/*
 * SPDX-FileCopyrightText: 2020 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.search.expr;

import java.util.List;

import com.top_logic.basic.config.ConfigurationException;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.basic.config.annotation.defaults.StringDefault;
import com.top_logic.model.TLObject;
import com.top_logic.model.TLStructuredTypePart;
import com.top_logic.model.TLType;
import com.top_logic.model.search.expr.config.dom.Expr;
import com.top_logic.model.search.expr.config.operations.AbstractSimpleMethodBuilder;

/**
 * {@link GenericMethod} fetching a value from a {@link TLObject} where the
 * {@link TLStructuredTypePart part} to get value for is dynamically given by a
 * {@link SearchExpression}.
 * 
 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
 */
public class DynamicGet extends GenericMethod implements AccessLike {

	/**
	 * Creates a new {@link DynamicGet}.
	 */
	public DynamicGet(String name, SearchExpression self, SearchExpression[] arguments) {
		super(name, self, arguments);
	}

	@Override
	public GenericMethod copy(SearchExpression self, SearchExpression[] arguments) {
		return new DynamicGet(getName(), self, arguments);
	}

	@Override
	public TLType getType(TLType selfType, List<TLType> argumentTypes) {
		/* Unable to determine type of this method statically, because it depends on the type of the
		 * TLStructureTypePart to get value for. */
		return null;
	}

	@Override
	protected Object eval(Object self, Object[] arguments, EvalContext definitions) {
		Object arg0 = arguments[0];
		if (arg0 == null) {
			// Not yet set
			return null;
		}
		TLStructuredTypePart part = asTypePart(getArguments()[0], arg0);
		return evalPotentialFlatMap(definitions, self, part);
	}

	/**
	 * {@link AbstractSimpleMethodBuilder} creating {@link DynamicGet}.
	 */
	public static final class Builder extends AbstractSimpleMethodBuilder<DynamicGet> {

		/**
		 * Configuration for the {@link Builder}.
		 */
		public interface Config extends AbstractSimpleMethodBuilder.Config<Builder> {

			@StringDefault("dynamicGet")
			@Override
			String getName();

		}

		/**
		 * Creates a {@link Builder}.
		 */
		public Builder(InstantiationContext context, Config config) {
			super(context, config);
		}

		@Override
		public DynamicGet build(Expr expr, SearchExpression self, SearchExpression[] args)
				throws ConfigurationException {
			checkSingleArg(expr, args);
			return new DynamicGet(getConfig().getName(), self, args);
		}
	}

}
