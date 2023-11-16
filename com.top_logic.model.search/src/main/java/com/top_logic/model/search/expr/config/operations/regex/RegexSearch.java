/*
 * SPDX-FileCopyrightText: 2020 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.search.expr.config.operations.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * {@link GenericMethod} matching a regular expression pattern created by {@link Regex} to a string.
 * 
 * <p>
 * The result of the match is a list of {@link Match} instances. Each of them representing a found
 * occurrence of the pattern in the target string.
 * </p>
 * 
 * @see Match
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class RegexSearch extends SimpleGenericMethod {

	/**
	 * Creates a {@link RegexSearch}.
	 */
	protected RegexSearch(String name, SearchExpression[] arguments) {
		super(name, arguments);
	}

	@Override
	public GenericMethod copy(SearchExpression[] arguments) {
		return new RegexSearch(getName(), arguments);
	}

	@Override
	public TLType getType(List<TLType> argumentTypes) {
		return null;
	}

	@Override
	public Object eval(Object[] arguments) {
		Object input = arguments[1];
		if (input == null) {
			return null;
		}
		Pattern pattern = (Pattern) arguments[0];
		String text = asString(input);
		Matcher matcher = pattern.matcher(text);
		List<Match> result = new ArrayList<>();
		while (matcher.find()) {
			result.add(Match.from(text, matcher));
		}
		return result;
	}

	/**
	 * {@link MethodBuilder} creating {@link RegexSearch}.
	 */
	public static final class Builder extends AbstractSimpleMethodBuilder<RegexSearch> {
		/**
		 * Creates a {@link Builder}.
		 */
		public Builder(InstantiationContext context, Config<?> config) {
			super(context, config);
		}

		@Override
		public RegexSearch build(Expr expr, SearchExpression self, SearchExpression[] args) throws ConfigurationException {
			checkTwoArgs(expr, args);
			return new RegexSearch(getConfig().getName(), args);
		}

	}
}
