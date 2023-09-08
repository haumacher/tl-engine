/*
 * SPDX-FileCopyrightText: 2009 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.element.genericimport.converterfunction;

import com.top_logic.element.genericimport.interfaces.GenericCache;
import com.top_logic.element.genericimport.interfaces.GenericConverterFunction;
import com.top_logic.util.Country;


/**
 * {@link GenericConverterFunction} that interprets the input as country ISO codes.
 * 
 * @author <a href="mailto:fsc@top-logic.com">Friedemann Schneider</a>
 */
public class CountryMapping implements GenericConverterFunction {
    @Override
	public Object map(Object aInput, GenericCache aCache) {
        if (aInput != null) {
            return new Country(String.valueOf(aInput));
        }
        return null;
    }
}

