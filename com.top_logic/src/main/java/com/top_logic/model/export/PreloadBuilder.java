/*
 * SPDX-FileCopyrightText: 2012 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.export;

/**
 * Builder for {@link PreloadOperation} aggregates.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public interface PreloadBuilder {

	/**
	 * Adds the given {@link PreloadOperation} to the preload being built.
	 */
	void addPreload(PreloadOperation operation);

}