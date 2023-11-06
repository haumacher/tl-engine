/*
 * SPDX-FileCopyrightText: 2023 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.monitoring.log;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.top_logic.basic.col.MapBuilder;
import com.top_logic.basic.logging.Level;
import com.top_logic.basic.tools.NameBuilder;

/**
 * The severity of a {@link LogLine}.
 * <p>
 * As Log4j supports custom log severities, this cannot be a fixed enum.
 * </p>
 * <p>
 * Using the Log4j <code>org.apache.logging.log4j.Level</code> type or even just referencing it and
 * its constants is not possible, as Top-Logic can be used without Log4j and therefore must not have
 * any compile time dependencies to it.
 * </p>
 * <p>
 * This class is thread-safe.
 * </p>
 * 
 * @author <a href=mailto:jst@top-logic.com>Jan Stolzenburg</a>
 */
public final class LogLineSeverity implements Comparable<LogLineSeverity> {

	/** @see #cleanUpSeverities() */
	private static final int MAX_SEVERITIES = 100;

	/** {@link Level#FATAL} */
	public static final LogLineSeverity FATAL = new LogLineSeverity("FATAL", 6000);

	/** {@link Level#ERROR} */
	public static final LogLineSeverity ERROR = new LogLineSeverity("ERROR", 5000);

	/** {@link Level#WARN} */
	public static final LogLineSeverity WARN = new LogLineSeverity("WARN", 4000);

	/** {@link Level#INFO} */
	public static final LogLineSeverity INFO = new LogLineSeverity("INFO", 3000);

	/** {@link Level#DEBUG} */
	public static final LogLineSeverity DEBUG = new LogLineSeverity("DEBUG", 2000);

	/** Not used in Top-Logic, but one of the official Log4j log levels. */
	public static final LogLineSeverity TRACE = new LogLineSeverity("TRACE", 1000);

	/** The sort order if none is specified. */
	public static final int DEFAULT_SORT_ORDER = 0;

	private static final Map<String, LogLineSeverity> SEVERITIES =
		new ConcurrentHashMap<>(new MapBuilder<String, LogLineSeverity>()
			.put(FATAL.getName(), FATAL)
			.put(ERROR.getName(), ERROR)
			.put(WARN.getName(), WARN)
			.put(INFO.getName(), INFO)
			.put(DEBUG.getName(), DEBUG)
			.put(TRACE.getName(), TRACE)
			.toMap());

	private final String _name;

	private final int _sortOder;

	private final String _cssClass;

	private LogLineSeverity(String name, int sortOrder) {
		_name = name;
		_sortOder = sortOrder;
		/* Using a non-compiled regex is no problem here, as new log levels should be very, very rare. */
		_cssClass = "tl-log-lines-table--" + name.toLowerCase().replaceAll("[^a-z0-9_-]", "");
	}

	/** Retrieves the severity with the given name or creates if it does not exist. */
	public static LogLineSeverity getOrCreate(String name) {
		if (SEVERITIES.size() > MAX_SEVERITIES) {
			/* Prevent filling up this static cache by corrupted or wrongly parsed log files. If
			 * there are that many log severities, something went wrong. Clearing the cache is not a
			 * problem: There is no problem when two objects for the same severity exist: This class
			 * overrides 'equals', 'hashCode' and implements 'Comparable' in a way that can handle
			 * that. Multiple objects for the same severity just uses unnecessary memory. But that
			 * is no problem, compared with the memory hole that would be caused by this cache it if
			 * filled up with too many severities . */
			cleanUpSeverities();
		}
		return SEVERITIES.computeIfAbsent(name, id -> new LogLineSeverity(id, DEFAULT_SORT_ORDER));
	}

	private static void cleanUpSeverities() {
		SEVERITIES.clear();
		SEVERITIES.put(FATAL.getName(), FATAL);
		SEVERITIES.put(ERROR.getName(), ERROR);
		SEVERITIES.put(WARN.getName(), WARN);
		SEVERITIES.put(INFO.getName(), INFO);
		SEVERITIES.put(DEBUG.getName(), DEBUG);
		SEVERITIES.put(TRACE.getName(), TRACE);
	}

	/** The text in log files used for this severity. */
	public String getName() {
		return _name;
	}

	/** A number for sorting the severity: Higher numbers means higher severity. */
	public int getSortOrder() {
		return _sortOder;
	}

	/** The CSS class for displaying a {@link LogLine} with this severity. */
	public String getCssClass() {
		return _cssClass;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_name, _sortOder);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LogLineSeverity)) {
			return false;
		}
		LogLineSeverity otherSeverity = (LogLineSeverity) other;
		return Objects.equals(getName(), otherSeverity.getName())
			&& getSortOrder() == otherSeverity.getSortOrder();
	}

	@Override
	public int compareTo(LogLineSeverity other) {
		if (other == null) {
			/* "null" has the least severity: If a message does not even have a severity, it
			 * shouldn't be important. */
			return 1;
		}
		return Integer.compare(getSortOrder(), other.getSortOrder());
	}

	@Override
	public String toString() {
		return new NameBuilder(this)
			.addUnnamed(getName())
			.add("sort-order", getSortOrder())
			.build();
	}

}
