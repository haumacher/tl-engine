/*
 * SPDX-FileCopyrightText: 2010 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.form.selection;

import org.w3c.dom.Document;

import com.top_logic.basic.col.Provider;
import com.top_logic.layout.form.model.SelectField;

/**
 * The class {@link SingleSelectDialog} provides dialogs in multi select style for editing selection
 * of a {@link SelectField}.
 * 
 * @author <a href="mailto:sts@top-logic.com">sts</a>
 */
public class SingleSelectDialog extends ListSelectDialog {

	/**
	 * Creates a {@link SingleSelectDialog}.
	 * 
	 * @param targetSelectField
	 *        See {@link AbstractSelectDialog#AbstractSelectDialog(SelectField, SelectDialogConfig)}.
	 * @param config
	 *        See {@link SelectDialogBase#SelectDialogBase(SelectField, SelectDialogConfig)}.
	 */
	public SingleSelectDialog(SelectField targetSelectField, SelectDialogConfig config) {
		super(targetSelectField, config);
	}

	@Override
	protected Provider<Document> getTemplateProvider() {
		return new SingleSelectTemplateBuilder(getConfig(), isLarge());
	}
}
