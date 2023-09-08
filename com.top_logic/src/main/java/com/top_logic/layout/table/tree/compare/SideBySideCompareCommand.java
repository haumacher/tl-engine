/*
 * SPDX-FileCopyrightText: 2018 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.table.tree.compare;

import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.basic.config.annotation.defaults.FormattedDefault;
import com.top_logic.basic.util.ResKey;
import com.top_logic.layout.basic.ThemeImage;
import com.top_logic.tool.boundsec.AbstractCommandHandler;

/**
 * {@link AbstractCompareModeCommand}, that switches the {@link CompareTreeTableComponent} to
 * {@link CompareMode#SIDE_BY_SIDE}.
 * 
 * @author <a href="mailto:sts@top-logic.com">Stefan Steinert</a>
 */
public class SideBySideCompareCommand extends AbstractCompareModeCommand {

	/** Default command name for {@link SideBySideCompareCommand} */
	public static final String COMMAND_NAME = "switchToSideBySideCompareView";

	/** Default image for the command opening a side by side compare view. */
	static final String COMMAND_IMAGE_KEY = "/misc/compare/side-by-side-view.png";

	/**
	 * Configuration of {@link SideBySideCompareCommand}.
	 */
	public interface Config extends AbstractCommandHandler.Config {

		@Override
		@FormattedDefault(COMMAND_IMAGE_KEY)
		ThemeImage getImage();

	}

	/**
	 * Create a new {@link SideBySideCompareCommand}.
	 */
	public SideBySideCompareCommand(InstantiationContext context, Config config) {
		super(context, config);
	}

	@Override
	protected CompareMode getExecutableCompareMode() {
		return CompareMode.SIDE_BY_SIDE;
	}

	@Override
	protected ResKey getDefaultI18NKey() {
		return I18NConstants.OPEN_SIDE_BY_SIDE_COMPARE_VIEW;
	}

}
