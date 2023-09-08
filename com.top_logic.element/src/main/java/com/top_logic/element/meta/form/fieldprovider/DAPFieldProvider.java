/*
 * SPDX-FileCopyrightText: 2013 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.element.meta.form.fieldprovider;

import java.util.ArrayList;

import com.top_logic.model.TLStructuredTypePart;
import com.top_logic.element.meta.form.AbstractFieldProvider;
import com.top_logic.element.meta.form.EditContext;
import com.top_logic.element.meta.form.FieldProvider;
import com.top_logic.layout.form.Constraint;
import com.top_logic.layout.form.FormMember;
import com.top_logic.layout.form.constraints.GenericMandatoryConstraint;
import com.top_logic.layout.form.model.FormFactory;
import com.top_logic.layout.form.model.SelectField;

/**
 * {@link FieldProvider} for {@link TLStructuredTypePart}s refering to external systems.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class DAPFieldProvider extends AbstractFieldProvider {

	@Override
	public FormMember getFormField(EditContext editContext, String fieldName) {
		boolean isMandatory = editContext.isMandatory();
		boolean isDisabled = editContext.isDisabled();
		boolean isSearch = editContext.isSearchUpdate();

		Constraint mandatoryChecker = isMandatory ? GenericMandatoryConstraint.SINGLETON
			: null;

		if (isSearch) {
			return null;
		}

		return FormFactory.newSelectField(fieldName, /* TODO: BHU: where to get the options from? */
			new ArrayList(), false, SelectField.NO_SELECTION,
			isMandatory, isDisabled, mandatoryChecker);
	}

}
