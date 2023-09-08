/*
 * SPDX-FileCopyrightText: 2016 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.util.monitor.db;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.top_logic.basic.CalledByReflection;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.basic.sql.LoggingDataSourceProxy.AggregatingAnalyzer;
import com.top_logic.basic.sql.LoggingDataSourceProxy.Statistics;
import com.top_logic.layout.DisplayContext;
import com.top_logic.mig.html.layout.LayoutComponent;
import com.top_logic.tool.boundsec.CommandHandler;
import com.top_logic.tool.boundsec.HandlerResult;

/**
 * {@link CommandHandler} finishing the monitoring process.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class StopMonitoringCommand extends MonitoringCommand {

	/**
	 * Creates a {@link StopMonitoringCommand} from configuration.
	 * 
	 * @param context
	 *        The context for instantiating sub configurations.
	 * @param config
	 *        The configuration.
	 */
	@CalledByReflection
	public StopMonitoringCommand(InstantiationContext context, Config config) {
		super(context, config);
	}

	@Override
	public HandlerResult handleCommand(DisplayContext aContext, LayoutComponent aComponent,
			Object model, Map<String, Object> someArguments) {

		AggregatingAnalyzer analyzer = MonitoringCommand.removeResult(aComponent);
		installAnalyzer(null);

		List<Entry<String, Statistics>> result = analyzer.take();
		if (!result.isEmpty()) {
			aComponent.setModel(result);
		}

		return HandlerResult.DEFAULT_RESULT;
	}

}
