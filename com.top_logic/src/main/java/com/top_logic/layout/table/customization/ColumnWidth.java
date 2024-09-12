/*
 * SPDX-FileCopyrightText: 2024 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.table.customization;

import com.top_logic.basic.CalledByReflection;
import com.top_logic.basic.annotation.InApp;
import com.top_logic.basic.config.AbstractConfiguredInstance;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.basic.config.PolymorphicConfiguration;
import com.top_logic.basic.config.annotation.Label;
import com.top_logic.basic.config.annotation.TagName;
import com.top_logic.layout.DisplayDimension;
import com.top_logic.layout.table.model.ColumnConfiguration;
import com.top_logic.layout.table.model.ColumnConfigurator;

/**
 * Configures the width of a column.
 */
@InApp
public class ColumnWidth extends AbstractConfiguredInstance<ColumnWidth.Config<?>>
		implements ColumnConfigurator {

	/**
	 * Configuration options for {@link ColumnWidth}.
	 */
	@TagName("column-width")
	public interface Config<I extends ColumnWidth> extends PolymorphicConfiguration<I> {
		/**
		 * The width of the column.
		 * 
		 * <p>
		 * The value must be given in pixels (px) for tables that allow for drag and drop width
		 * customization by the user.
		 * </p>
		 */
		@Label("Column width")
		DisplayDimension getValue();
	}

	/**
	 * Creates a {@link ColumnWidth} from configuration.
	 * 
	 * @param context
	 *        The context for instantiating sub configurations.
	 * @param config
	 *        The configuration.
	 */
	@CalledByReflection
	public ColumnWidth(InstantiationContext context, Config<?> config) {
		super(context, config);
	}

	@Override
	public void adapt(ColumnConfiguration column) {
		column.setDefaultColumnWidth(getConfig().getValue().toString());
	}

}
