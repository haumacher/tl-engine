/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.element.layout.formeditor.definition;

import com.top_logic.basic.annotation.InApp;
import com.top_logic.basic.config.annotation.DerivedRef;
import com.top_logic.basic.config.annotation.Hidden;
import com.top_logic.basic.config.annotation.Name;
import com.top_logic.basic.config.annotation.Step;
import com.top_logic.basic.config.annotation.TagName;
import com.top_logic.element.layout.formeditor.FormTypeProperty;
import com.top_logic.element.layout.meta.TLStructuredTypeFormBuilder.EditModel;
import com.top_logic.layout.form.values.edit.annotation.ItemDisplay;
import com.top_logic.layout.form.values.edit.annotation.ItemDisplay.ItemDisplayType;
import com.top_logic.model.TLStructuredType;
import com.top_logic.model.annotate.TLTypeKind;
import com.top_logic.model.annotate.TargetType;
import com.top_logic.model.config.TLTypeAnnotation;
import com.top_logic.model.form.definition.FormDefinition;

/**
 * A {@link TLTypeAnnotation} to represent the structure of a form.
 * 
 * @author <a href="mailto:iwi@top-logic.com">Isabell Wittich</a>
 */
@TagName("form-definition")
@TargetType(value = { TLTypeKind.REF, TLTypeKind.COMPOSITION })
@InApp
public interface TLFormDefinition extends TLTypeAnnotation {

	/**
	 * Property name {@link #getEditedType()}
	 */
	String EDITED_TYPE = "edited-type";

	/**
	 * The {@link FormDefinition} of the annotated type.
	 */
	@FormTypeProperty(EDITED_TYPE)
	@ItemDisplay(ItemDisplayType.VALUE)
	FormDefinition getForm();

	/**
	 * @see #getForm()
	 */
	void setForm(FormDefinition form);

	/**
	 * The current type.
	 */
	@Name(EDITED_TYPE)
	@Hidden
	@DerivedRef(steps = { @Step(ANNOTATED), @Step(value = EditModel.EDITED_TYPE, type = EditModel.class) })
	TLStructuredType getEditedType();
}
