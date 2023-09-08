/*
 * SPDX-FileCopyrightText: 2012 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.form.component;

import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.basic.config.annotation.defaults.FormattedDefault;
import com.top_logic.tool.boundsec.CommandGroupReference;
import com.top_logic.tool.boundsec.simple.SimpleBoundCommandGroup;

/**
 * DefaultSaveCommandHandler with restricted write command group.
 *
 * @author <a href="mailto:Christian.Braun@top-logic.com">Christian Braun</a>
 */
public class RestrictedDefaultSaveCommandHandler extends DefaultSaveCommandHandler {

    /** The ID of this command. */
    public static final String COMMAND_ID = "restrictedDefaultSave";

	/**
	 * Configuration for {@link RestrictedDefaultSaveCommandHandler}.
	 * 
	 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
	 */
	public interface Config extends DefaultSaveCommandHandler.Config {

		@FormattedDefault(SimpleBoundCommandGroup.RESTRICTED_WRITE_NAME)
		@Override
		CommandGroupReference getGroup();

	}

	/**
	 * Creates a new instance of this class.
	 */
    public RestrictedDefaultSaveCommandHandler(InstantiationContext context, Config config) {
		super(context, config);
    }

}
