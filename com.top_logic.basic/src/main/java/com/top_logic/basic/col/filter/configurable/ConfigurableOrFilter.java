/*
 * SPDX-FileCopyrightText: 2016 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.basic.col.filter.configurable;

import com.top_logic.basic.CalledByReflection;
import com.top_logic.basic.UnreachableAssertion;
import com.top_logic.basic.col.filter.typed.FilterResult;
import com.top_logic.basic.col.filter.typed.TypedFilter;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.basic.config.PolymorphicConfiguration;
import com.top_logic.basic.config.TypedConfiguration;
import com.top_logic.basic.config.annotation.TagName;

/**
 * A {@link ConfigurableCompositeFilter} for 'or'.
 * <p>
 * The inner filters are asked in order. If one of them {@link FilterResult#TRUE}s an object, the
 * remaining filters are not asked. Only the filters that {@link TypedFilter#getType() can filter}
 * an object are asked.
 * </p>
 * <p>
 * Returns {@link FilterResult#INAPPLICABLE} if none of inner filters is applicable to the given
 * object.
 * </p>
 * 
 * @author <a href="mailto:jst@top-logic.com">Jan Stolzenburg</a>
 */
public final class ConfigurableOrFilter extends ConfigurableCompositeFilter<ConfigurableOrFilter.Config> {

	/**
	 * The {@link TypedConfiguration} of the {@link ConfigurableOrFilter}.
	 */
	@TagName("or")
	public interface Config extends ConfigurableCompositeFilter.Config<ConfigurableOrFilter> {
		// Nothing needed by the type parameter declaration
	}

	/**
	 * Called by the {@link TypedConfiguration} for creating a {@link ConfigurableOrFilter}.
	 * <p>
	 * <b>Don't call directly.</b> Use
	 * {@link InstantiationContext#getInstance(PolymorphicConfiguration)} instead.
	 * </p>
	 * 
	 * @param context
	 *        For error reporting and instantiation of dependent configured objects.
	 * @param config
	 *        The configuration for the new instance.
	 */
	@CalledByReflection
	public ConfigurableOrFilter(InstantiationContext context, Config config) {
		super(context, config);
	}

	@Override
	public FilterResult matchesTypesafe(Object object) {
		FilterResult result = FilterResult.INAPPLICABLE;
		for (TypedFilter child : getFilters()) {
			FilterResult innerResult = child.matches(object);
			switch (innerResult) {
				case TRUE: {
					return FilterResult.TRUE;
				}
				case FALSE: {
					result = FilterResult.FALSE;
					continue;
				}
				case INAPPLICABLE: {
					continue;
				}
				default: {
					throw new UnreachableAssertion("Unexpected value: " + innerResult);
				}
			}
		}
		return result;
	}

}
