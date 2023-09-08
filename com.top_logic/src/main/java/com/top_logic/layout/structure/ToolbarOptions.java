/*
 * SPDX-FileCopyrightText: 2016 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.structure;

import com.top_logic.basic.config.ConfigurationItem;
import com.top_logic.basic.config.Decision;
import com.top_logic.basic.config.annotation.Name;

/**
 * Configuration options that describe toolbar properties.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public interface ToolbarOptions extends ConfigurationItem {

	/**
	 * @see #getShowMaximize()
	 */
	String SHOW_MAXIMIZE = "showMaximize";

	/**
	 * @see #getShowMinimize()
	 */
	String SHOW_MINIMIZE = "showMinimize";

	/**
	 * Whether the maximize button is shown in the toolbar.
	 */
	@Name(SHOW_MAXIMIZE)
	Decision getShowMaximize();

	/**
	 * @see #getShowMaximize()
	 */
	void setShowMaximize(Decision value);

	/**
	 * Whether the minimize button is shown in the toolbar.
	 */
	@Name(SHOW_MINIMIZE)
	Decision getShowMinimize();

	/**
	 * @see #getShowMaximize()
	 */
	void setShowMinimize(Decision value);

}
