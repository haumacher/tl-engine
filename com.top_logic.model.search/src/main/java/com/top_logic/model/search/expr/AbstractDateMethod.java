/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.search.expr;

import java.util.Date;

/**
 * Base class for {@link GenericMethod}s dealing with {@link Date} values.
 *
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public abstract class AbstractDateMethod extends GenericMethod {

	/**
	 * Creates a {@link AbstractDateMethod}.
	 */
	protected AbstractDateMethod(String name, SearchExpression[] arguments) {
		super(name, arguments);
	}

}
