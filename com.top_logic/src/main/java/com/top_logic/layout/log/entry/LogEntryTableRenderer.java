/*
 * SPDX-FileCopyrightText: 2023 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.log.entry;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collection;

import com.top_logic.base.services.simpleajax.HTMLFragment;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.basic.config.TypedConfiguration;
import com.top_logic.basic.util.ResKey;
import com.top_logic.basic.xml.TagWriter;
import com.top_logic.layout.table.TableViewModel;
import com.top_logic.layout.table.control.TableControl;
import com.top_logic.layout.table.renderer.DefaultTableRenderer;
import com.top_logic.util.Resources;

/**
 * {@link DefaultTableRenderer} that writes an additional footer which summarizes the number of
 * errors and warnings.
 * 
 * @author <a href=mailto:jst@top-logic.com>Jan Stolzenburg</a>
 */
public class LogEntryTableRenderer extends DefaultTableRenderer {

	/** {@link TypedConfiguration} constructor for {@link LogEntryTableRenderer}. */
	public LogEntryTableRenderer(InstantiationContext context, Config config) {
		super(context, config);
	}

	@Override
	protected HTMLFragment createFooterTextFragment(TableControl view) {
		return (HTMLFragment) (context, out) -> {
			writeLogEntrySummary(out, view.getViewModel());
		};
	}

	private void writeLogEntrySummary(TagWriter out, TableViewModel viewModel) {
		int[] displayedRows = countDisplayedRows(viewModel.getDisplayedRows());
		int displayedErrors = displayedRows[0];
		int displayedWarnings = displayedRows[1];
		int displayedLogEntries = displayedRows[2];

		int[] allRows = countDisplayedRows(viewModel.getAllRows());
		int allErrors = allRows[0];
		int allWarnings = allRows[1];
		int allLogEntries = allRows[2];

		// Count files, all errors and warnings:
		ResKey errorsMessage = I18NConstants.TABLE_FOOTER_ERRORS__DISPLAYED_ALL.fill(displayedErrors, allErrors);
		ResKey warningsMessage = I18NConstants.TABLE_FOOTER_WARNINGS__DISPLAYED_ALL.fill(displayedWarnings, allWarnings);
		ResKey rowsMessage = I18NConstants.TABLE_FOOTER_ROWS__DISPLAYED_ALL.fill(displayedLogEntries, allLogEntries);

		/* Don't count "fatal": That level is not really used. Including it in "error" is good
		 * enough. */
		if (allErrors != 0) {
			writeSummary(out, LogEntrySeverity.ERROR.getCssClass(), errorsMessage);
			out.writeText(", ");
		}
		if (allWarnings != 0) {
			writeSummary(out, LogEntrySeverity.WARN.getCssClass(), warningsMessage);
			out.writeText(", ");
		}
		/* Don't count the other levels individually: They are not important enough. */
		writeSummary(out, null, rowsMessage);
	}

	private int[] countDisplayedRows(Collection<?> rows) {
		int errors = 0;
		int warnings = 0;
		for (Object row : rows) {
			ParsedLogEntry logEntry = (ParsedLogEntry) row;
			if (isErrorOrWorse(logEntry)) {
				/* Errors and above. */
				errors += 1;
			} else if (isWarningOrWorse(logEntry)) {
				/* Warnings and, in case of custom log levels, above which are not errors. */
				warnings += 1;
			}
		}
		return new int[] { errors, warnings, rows.size() };
	}

	private boolean isErrorOrWorse(ParsedLogEntry logEntry) {
		return logEntry.getSeverity().getSortOrder() >= LogEntrySeverity.ERROR.getSortOrder();
	}

	private boolean isWarningOrWorse(ParsedLogEntry logEntry) {
		return logEntry.getSeverity().getSortOrder() >= LogEntrySeverity.WARN.getSortOrder();
	}

	private void writeSummary(TagWriter out, String cssClass, ResKey message) {
		out.beginBeginTag(SPAN);
		if (cssClass != null) {
			out.beginCssClasses();
			writeAttributeText(out, cssClass);
			out.endCssClasses();
		}
		out.endBeginTag();
		out.writeText(translate(message));
		out.endTag(SPAN);
	}

	private void writeAttributeText(TagWriter out, String text) {
		try {
			out.writeAttributeText(text);
		} catch (IOException exception) {
			throw new UncheckedIOException(exception);
		}
	}

	private String translate(ResKey resKey) {
		return Resources.getInstance().getString(resKey);
	}

}
