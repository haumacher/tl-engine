/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.bpe.bpml.display;

import com.top_logic.basic.config.annotation.Abstract;
import com.top_logic.basic.config.annotation.Hidden;
import com.top_logic.basic.config.annotation.Name;
import com.top_logic.basic.config.container.ConfigPart;
import com.top_logic.element.meta.form.fieldprovider.ConfigurationFieldProvider.EditContext;

/**
 * UI part that is connected to the {@link EditContext}.
 *
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
@Abstract
public interface EditContextPart extends ConfigPart {

	/**
	 * @see #getContext()
	 */
	String CONTEXT = "context";

	/**
	 * The {@link EditContext}.
	 */
	@Name(CONTEXT)
	@Hidden
	EditContext getContext();

}
