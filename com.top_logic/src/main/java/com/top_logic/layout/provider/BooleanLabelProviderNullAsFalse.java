/*
 * SPDX-FileCopyrightText: 2008 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.provider;

import com.top_logic.layout.LabelProvider;
import com.top_logic.layout.form.I18NConstants;
import com.top_logic.util.Resources;

/**
 * @author    <a href="mailto:mga@top-logic.com">Michael G�nsler</a>
 */
public class BooleanLabelProviderNullAsFalse implements LabelProvider {

	/**
	 * Singleton {@link BooleanLabelProviderNullAsFalse} instance.
	 */
	public static final BooleanLabelProviderNullAsFalse INSTANCE = new BooleanLabelProviderNullAsFalse();

	private BooleanLabelProviderNullAsFalse() {
		// Singleton constructor.
	}

    @Override
	public String getLabel(Object anObject) {
        if (anObject instanceof Boolean) {
			return Resources.getInstance().getString(
				((Boolean) anObject).booleanValue() ? I18NConstants.TRUE_LABEL : I18NConstants.FALSE_LABEL);
        }
		if (anObject == null) {
			return Resources.getInstance().getString(I18NConstants.FALSE_LABEL);
        }
		return "?";
    }
}

