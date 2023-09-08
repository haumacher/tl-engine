/*
 * SPDX-FileCopyrightText: 2009 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.scripting.recorder.ref.misc;

import com.top_logic.basic.config.NamedConfiguration;
import com.top_logic.layout.scripting.recorder.ref.ModelName;

/**
 * An attribute value assignment. 
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public interface AttributeValue extends NamedConfiguration {

	/**
	 * The value that is assigned to the attribute.
	 */
	ModelName getValue();

	/**
	 * Sets the value of {@link #getValue()}.
	 */
	void setValue(ModelName value);

}
