/*
 * SPDX-FileCopyrightText: 2009 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.scripting.action;

import com.top_logic.basic.config.annotation.defaults.ClassDefault;
import com.top_logic.layout.scripting.runtime.action.LogoutActionOp;

/**
 * {@link ApplicationAction} configuration for the logout action.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public interface LogoutAction extends ApplicationAction {

	@Override
	@ClassDefault(LogoutActionOp.class)
	Class<LogoutActionOp> getImplementationClass();
}
