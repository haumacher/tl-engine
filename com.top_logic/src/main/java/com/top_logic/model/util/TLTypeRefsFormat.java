/*
 * SPDX-FileCopyrightText: 2021 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.util;

import com.top_logic.basic.config.ConfigurationValueProvider;
import com.top_logic.model.TLType;

/**
 * {@link ConfigurationValueProvider} that resolves {@link TLType}s by comma separated full
 * qualified names.
 * 
 * @author <a href="mailto:sfo@top-logic.com">sfo</a>
 */
public class TLTypeRefsFormat extends ListFormat<TLModelPartRef> {

	/**
	 * Creates a {@link ListFormat} for {@link TLModelPartRef}s.
	 */
	public TLTypeRefsFormat() {
		super(TLModelPartRef.TypePartRefValueProvider.INSTANCE, ",");
	}

}
