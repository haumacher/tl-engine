/*
 * SPDX-FileCopyrightText: 2015 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.basic.config;

import com.top_logic.basic.config.annotation.Mandatory;
import com.top_logic.basic.config.annotation.Subtypes;


/**
 * Configuration wrapper adding a name to an instance configuration.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public interface NamedInstanceConfig<T> extends NamedConfigMandatory {

	/**
	 * Configuration of the implementation class.
	 */
	@Mandatory
	@Subtypes({})
	PolymorphicConfiguration<T> getImpl();

}
