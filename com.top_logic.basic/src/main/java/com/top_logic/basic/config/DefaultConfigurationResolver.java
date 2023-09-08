/*
 * SPDX-FileCopyrightText: 2009 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.basic.config;

import com.top_logic.basic.config.DefaultConfigConstructorScheme.Factory;

/**
 * {@link ConfigurationResolver} using the
 * {@link DefaultConfigConstructorScheme} for finding configuration interfaces
 * for implementation classes.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class DefaultConfigurationResolver implements ConfigurationResolver {

	@Override
	public ConfigurationDescriptor getConfigurationDescriptor(Class implClass) throws ConfigurationException {
		Factory factory = DefaultConfigConstructorScheme.getFactory(implClass);
		Class concreteConfigInterface = factory.getConfigurationInterface();
		ConfigurationDescriptor concreteDescriptor = TypedConfiguration.getConfigurationDescriptor(concreteConfigInterface);
		return concreteDescriptor;
	}


}
