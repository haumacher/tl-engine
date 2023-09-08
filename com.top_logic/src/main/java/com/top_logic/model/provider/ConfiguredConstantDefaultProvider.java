/*
 * SPDX-FileCopyrightText: 2017 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.provider;

import com.top_logic.basic.config.ConfiguredInstance;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.basic.config.PolymorphicConfiguration;
import com.top_logic.basic.config.annotation.Abstract;
import com.top_logic.basic.config.annotation.Label;

/**
 * {@link ConstantDefaultProvider} fetching the constant value form the configuration.
 * 
 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
 */
@Label("Default value computation")
public abstract class ConfiguredConstantDefaultProvider extends ConstantDefaultProvider
		implements ConfiguredInstance<ConfiguredConstantDefaultProvider.Config<?>> {

	/**
	 * The configuration of a {@link ConfiguredConstantDefaultProvider}.
	 * 
	 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
	 */
	@Abstract
	public interface Config<T extends ConfiguredConstantDefaultProvider> extends PolymorphicConfiguration<T> {

		/**
		 * The constant value to use as default.
		 * 
		 * @see DefaultProvider#createDefault(Object, com.top_logic.model.TLStructuredTypePart,
		 *      boolean)
		 */
		@Abstract
		Object getValue();
	}

	private final Config<?> _config;

	/**
	 * Creates a new {@link ConfiguredConstantDefaultProvider} from the given configuration.
	 * 
	 * @param context
	 *        {@link InstantiationContext} to instantiate sub configurations.
	 * @param config
	 *        Configuration for this {@link ConfiguredConstantDefaultProvider}.
	 */
	public ConfiguredConstantDefaultProvider(InstantiationContext context, Config<?> config) {
		super(config.getValue());
		_config = config;
	}

	@Override
	public Config<?> getConfig() {
		return _config;
	}

}

