/*
 * SPDX-FileCopyrightText: 2018 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.table.dnd;

import com.top_logic.layout.table.TableData;

/**
 * {@link TableDragSource} that prevents dragging completely.
 *
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class NoTableDrag extends DefaultTableDrag {

	/**
	 * Singleton {@link NoTableDrag} instance.
	 */
	@SuppressWarnings("hiding")
	public static final NoTableDrag INSTANCE = new NoTableDrag();

	private NoTableDrag() {
		// Singleton constructor.
	}

	@Override
	public boolean dragEnabled(TableData data) {
		return false;
	}

	@Override
	public boolean dragEnabled(TableData tableData, Object rowObject) {
		return false;
	}

}
