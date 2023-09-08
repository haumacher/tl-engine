/*
 * SPDX-FileCopyrightText: 2018 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.search.ui.model;

import com.top_logic.basic.config.annotation.DerivedRef;
import com.top_logic.layout.form.template.util.ResourceDisplay;
import com.top_logic.layout.form.values.edit.annotation.UseTemplate;
import com.top_logic.model.TLType;
import com.top_logic.model.search.ui.model.structure.SearchFilter;

/**
 * {@link SearchFilter} consisting of a combination of inner filters.
 *
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
@UseTemplate(ResourceDisplay.class)
public interface CombinedFilter extends SearchFilter, CombinationExpression, FilterContainer {

	@Override
	@DerivedRef({ CONTEXT, ValueContext.VALUE_TYPE })
	TLType getValueType();

	@Override
	@DerivedRef({ CONTEXT, ValueContext.VALUE_MULTIPLICITY })
	boolean getValueMultiplicity();

}
