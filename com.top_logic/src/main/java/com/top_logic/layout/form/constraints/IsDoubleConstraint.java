/*
 * SPDX-FileCopyrightText: 2008 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.form.constraints;

import com.top_logic.basic.StringServices;
import com.top_logic.layout.form.CheckException;
import com.top_logic.layout.form.I18NConstants;
import com.top_logic.util.Resources;

/**
 * Check if the string is a parseable Double.
 *
 * @author <a href="mailto:CBR@top-logic.com">CBR</a>
 */
public class IsDoubleConstraint extends AbstractStringConstraint {

    public static final IsDoubleConstraint INSTANCE = new IsDoubleConstraint();

    @Override
	protected boolean checkString(String aValue) throws CheckException {
        // This is no mandatory constraint
        if (StringServices.isEmpty(aValue)) return true;
        try {
            checkDouble(Double.parseDouble(aValue));
            return true;
        }
        catch (CheckException e) {
            throw e;
        }
        catch (Exception e) {
			throw new CheckException(Resources.getInstance().getString(I18NConstants.INVALID_NUMBER_VALUE));
        }
    }


    protected void checkDouble(double aValue) throws CheckException {
        // nothing to check here - for subclasses only
    }

}
