/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.form.definition;

import com.top_logic.basic.config.annotation.TagName;
import com.top_logic.model.form.implementation.FormDefinitionTemplateProvider;

/**
 * Definition of a form.
 * 
 * @see FormDefinitionTemplateProvider
 * 
 * @author <a href="mailto:iwi@top-logic.com">Isabell Wittich</a>
 */
@TagName("form")
public interface FormDefinition extends ContainerDefinition<FormDefinitionTemplateProvider> {
	// has no extra attributes
}
