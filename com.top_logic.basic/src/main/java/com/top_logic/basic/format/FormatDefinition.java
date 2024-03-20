/*
 * SPDX-FileCopyrightText: 2014 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.basic.format;

import java.text.Format;
import java.util.Locale;
import java.util.TimeZone;

import com.top_logic.basic.config.AbstractConfiguredInstance;
import com.top_logic.basic.config.ConfigurationException;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.basic.config.PolymorphicConfiguration;
import com.top_logic.basic.format.configured.FormatterService;

/**
 * Configured definition (and factory) for a {@link Format}.
 * 
 * <p>
 * Acutal formats are instantiated using {@link #newFormat(FormatConfig, TimeZone, Locale)}.
 * </p>
 * 
 * @see FormatterService
 * 
 * @since 5.7.4
 * 
 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
 */
public abstract class FormatDefinition<C extends FormatDefinition.Config<?>> extends AbstractConfiguredInstance<C>
		implements FormatFactory {

	/**
	 * Configuration of a {@link FormatDefinition}.
	 */
	public interface Config<I extends FormatDefinition<?>> extends PolymorphicConfiguration<I> {
		// Pure marker interface.
	}

	/**
	 * Creates a new {@link FormatDefinition} from the given configuration.
	 * 
	 * @param context
	 *        {@link InstantiationContext} to instantiate sub configurations.
	 * @param config
	 *        Configuration for this {@link FormatDefinition}.
	 * 
	 * @throws ConfigurationException
	 *         iff configuration is invalid.
	 */
	public FormatDefinition(InstantiationContext context, C config) throws ConfigurationException {
		super(context, config);
	}

}

