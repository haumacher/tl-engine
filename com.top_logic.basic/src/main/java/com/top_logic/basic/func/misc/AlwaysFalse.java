/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.basic.func.misc;

import com.top_logic.basic.func.Function0;

/**
 * The constant function returning {@link Boolean#FALSE}.
 *
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class AlwaysFalse extends Function0<Boolean> {

	/**
	 * Singleton {@link AlwaysFalse} instance.
	 */
	public static final AlwaysFalse INSTANCE = new AlwaysFalse();

	private AlwaysFalse() {
		// Singleton constructor.
	}

	@Override
	public Boolean apply() {
		return Boolean.FALSE;
	}

}
