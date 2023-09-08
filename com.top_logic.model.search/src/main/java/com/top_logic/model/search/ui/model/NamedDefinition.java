/*
 * SPDX-FileCopyrightText: 2018 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.search.ui.model;

import com.top_logic.basic.config.annotation.Abstract;
import com.top_logic.basic.config.annotation.Name;

/**
 * A {@link ValueContext} with a name for the value.
 *
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
@Abstract
public interface NamedDefinition extends ValueContext {

	/**
	 * Property name of {@link #getName()}.
	 */
	String NAME = "name";

	/**
	 * Name for referring to the context object from inner expressions.
	 */
	@Name(NAME)
	String getName();

	/**
	 * @see #getName()
	 */
	void setName(String value);

}
