/*
 * SPDX-FileCopyrightText: 2013 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.form.template.model;

import com.top_logic.html.template.HTMLTemplateFragment;

/**
 * {@link HTMLTemplateFragment} with some structure.
 * 
 * @see #getContents()
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public abstract class AbstractFragment implements Template {
	private final HTMLTemplateFragment _contents;

	AbstractFragment(HTMLTemplateFragment contents) {
		_contents = contents;
	}

	/**
	 * The content template to be expanded.
	 */
	public HTMLTemplateFragment getContents() {
		return _contents;
	}

}