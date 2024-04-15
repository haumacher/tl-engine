/*
 * SPDX-FileCopyrightText: 2009 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.basic;

import java.util.Map;

import com.top_logic.layout.DisplayContext;
import com.top_logic.mig.html.layout.CommandDispatcher;
import com.top_logic.mig.html.layout.LayoutComponent;
import com.top_logic.tool.boundsec.CommandHandler;
import com.top_logic.tool.boundsec.HandlerResult;

/**
 * The class {@link CommandHandlerCommand} is a {@link Command} which executes
 * some {@link CommandHandler} and contains all necessary informations for it.
 * 
 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
 */
public class CommandHandlerCommand implements Command {

	private final Map<String, Object> _arguments;

	private final LayoutComponent _component;

	private final CommandHandler _command;

	/**
	 * Creates a {@link CommandHandlerCommand} to be executed on the given {@link LayoutComponent}
	 * with the given arguments.
	 */
	public CommandHandlerCommand(CommandHandler command, LayoutComponent component, Map<String, Object> arguments) {
		_command = command;
		_component = component;
		_arguments = arguments;
	}

	/** Creates a {@link CommandHandlerCommand} to be executed on the given {@link LayoutComponent}. */
	public CommandHandlerCommand(CommandHandler command, LayoutComponent component) {
		this(command, component, CommandHandler.NO_ARGS);
	}
	/**
	 * Dispatches its command to the {@link CommandDispatcher}.
	 * 
	 * @see Command#executeCommand(DisplayContext)
	 */
	@Override
	public HandlerResult executeCommand(DisplayContext context) {
		return CommandDispatcher.getInstance().dispatchCommand(_command, context, _component, _arguments);
	}

	/** The wrapped {@link CommandHandler}. */
	public CommandHandler getCommand() {
		return _command;
	}

	/** The {@link LayoutComponent} on which the command will be executed. */
	public LayoutComponent getComponent() {
		return _component;
	}

	/** The arguments for invoking the command. */
	public Map<String, Object> getArguments() {
		return _arguments;
	}

}
