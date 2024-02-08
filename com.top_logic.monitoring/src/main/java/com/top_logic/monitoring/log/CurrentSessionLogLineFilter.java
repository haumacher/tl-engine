/*
 * SPDX-FileCopyrightText: 2024 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.monitoring.log;

import java.util.Map;

import com.top_logic.layout.basic.DefaultDisplayContext;

/**
 * A {@link LogLineFilter} that accepts only log lines from the current session.
 * 
 * @author <a href=mailto:jst@top-logic.com>Jan Stolzenburg</a>
 */
public class CurrentSessionLogLineFilter implements LogLineFilter {

	private final String _currentSessionMark = getCurrentSessionMark();

	@Override
	public boolean accept(Map<String, Object> unparsedLine) {
		String message = (String) unparsedLine.get(LogLine.PROPERTY_MESSAGE);
		return message.contains(_currentSessionMark);
	}

	private static String getCurrentSessionMark() {
		return DefaultDisplayContext.getDisplayContext().asRequest().getSession(false).getId();
	}

}
