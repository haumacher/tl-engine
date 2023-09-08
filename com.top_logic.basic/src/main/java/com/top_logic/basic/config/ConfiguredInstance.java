/*
 * SPDX-FileCopyrightText: 2011 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.basic.config;

/**
 * Interface for classes configured through {@link PolymorphicConfiguration}s.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public interface ConfiguredInstance<C extends PolymorphicConfiguration<?>> {
	
	/**
	 * The configuration of this instance.
	 */
	C getConfig();

}
