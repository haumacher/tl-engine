/*
 * SPDX-FileCopyrightText: 2017 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.scripting.template.gui;

import com.top_logic.base.context.TLSubSessionContext;
import com.top_logic.basic.CalledByReflection;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.layout.DisplayContext;
import com.top_logic.layout.scripting.recorder.ScriptingRecorder;
import com.top_logic.mig.html.layout.LayoutComponent;
import com.top_logic.tool.boundsec.CommandHandler;
import com.top_logic.tool.boundsec.commandhandlers.ToggleCommandHandler;

/**
 * {@link CommandHandler} allowing to switch the property
 * {@link ScriptingRecorder#recordAllCommands(TLSubSessionContext) whether all commands are
 * recorded} in the session.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class SwitchRecordAllCommands extends ToggleCommandHandler {

	/**
	 * Creates a {@link SwitchRecordAllCommands} from configuration.
	 * 
	 * @param context
	 *        The context for instantiating sub configurations.
	 * @param config
	 *        The configuration.
	 */
	@CalledByReflection
	public SwitchRecordAllCommands(InstantiationContext context, Config config) {
		super(context, config);
	}

	@Override
	protected boolean getState(DisplayContext aContext, LayoutComponent component) {
		return ScriptingRecorder.recordAllCommands(aContext.getSubSessionContext());
	}

	@Override
	protected void setState(DisplayContext aContext, LayoutComponent component, boolean newValue) {
		ScriptingRecorder.setRecordAllCommands(aContext.getSubSessionContext(), newValue);
	}

}
