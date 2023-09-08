/*
 * SPDX-FileCopyrightText: 2009 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.scripting.runtime.action;

import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.layout.scripting.action.ComponentAction;
import com.top_logic.layout.scripting.runtime.ActionContext;
import com.top_logic.mig.html.layout.LayoutComponent;

/**
 * {@link ApplicationActionOp} that makes a given component visible.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public final class MakeVisibleOp extends ComponentActionOp<ComponentAction> {

	public MakeVisibleOp(InstantiationContext context, ComponentAction config) {
		super(context, config);
	}

	@Override
	public Object process(ActionContext context, LayoutComponent component, Object argument) {
		boolean visible = component.makeVisible();
		ApplicationAssertions.assertTrue(config,
			"Unable to make component '" + component.getName() + "' visible.", visible);
		return argument;
	}
}
