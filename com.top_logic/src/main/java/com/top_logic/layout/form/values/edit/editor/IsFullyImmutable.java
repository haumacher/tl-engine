/*
 * SPDX-FileCopyrightText: 2017 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.form.values.edit.editor;

import com.top_logic.basic.col.Mapping;
import com.top_logic.layout.form.model.FieldMode;

/**
 * Mapping deciding wether a given {@link FieldMode} is {@link FieldMode#IMMUTABLE}.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
final class IsFullyImmutable implements Mapping<FieldMode, Boolean> {
	
	/**
	 * Singleton {@link IsFullyImmutable} instance.
	 */
	public static final IsFullyImmutable INSTANCE = new IsFullyImmutable();
	
	private IsFullyImmutable() {
		// Singleton constructor.
	}

	@Override
	public Boolean map(FieldMode mode) {
		if (mode == null) {
			return Boolean.FALSE;
		}
		switch (mode) {
			case IMMUTABLE:
				return Boolean.TRUE;
			default:
				return Boolean.FALSE;
		}
	}
}