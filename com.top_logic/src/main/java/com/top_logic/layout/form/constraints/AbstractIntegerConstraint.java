/*
 * SPDX-FileCopyrightText: 2006 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.form.constraints;

import com.top_logic.layout.form.CheckException;
import com.top_logic.layout.form.Constraint;
import com.top_logic.layout.form.FormField;
import com.top_logic.layout.form.I18NConstants;
import com.top_logic.util.Resources;

/**
 * Abstract {@link Constraint} for {@link FormField} implementations that use
 * {@link Integer} objects as {@link FormField#getValue() values}.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public abstract class AbstractIntegerConstraint extends AbstractConstraint {

	@Override
	public final boolean check(Object value) throws CheckException {
	    if (value instanceof Integer || value == null) {
	        return checkInteger((Integer)value);
	    }
	    else if (value instanceof Number) {
			return checkInteger(Integer.valueOf(((Number) value).intValue()));
	    }
	    else {
			throw new CheckException(Resources.getInstance().getString(I18NConstants.INVALID_INT_VALUE));
	    }
	}

	/**
	 * Type-safe convenience method for {@link Constraint#check(Object)}.
	 *
	 * @see #check(Object) for parameters and result.
	 */
	protected abstract boolean checkInteger(Integer value) throws CheckException;
}
