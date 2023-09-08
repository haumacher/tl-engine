/*
 * SPDX-FileCopyrightText: 2016 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.knowledge.service;

import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.basic.db.schema.setup.SchemaSetup;

/**
 * {@link SchemaSetup} with {@link TypeSystemConfiguration additional configuration} for Java type
 * bindings.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class TypeSystemSetup extends SchemaSetup {

	/**
	 * Creates a {@link TypeSystemSetup} from configuration.
	 */
	public TypeSystemSetup(InstantiationContext context, TypeSystemConfiguration config) {
		super(context, config);
	}

}
