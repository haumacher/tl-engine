/*
 * SPDX-FileCopyrightText: 2013 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.basic.config;


/**
 * {@link Initializer} that does nothing.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
final class EmptyInitializer extends Initializer {

	static final Initializer EMPTY_INITIALIZER = new EmptyInitializer();

	@Override
	public void init(AbstractConfigItem item) {
		// Nothing to do.
	}

}