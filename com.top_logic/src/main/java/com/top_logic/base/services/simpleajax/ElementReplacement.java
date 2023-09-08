/*
 * SPDX-FileCopyrightText: 2006 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.base.services.simpleajax;

/**
 * An action that replaces a whole element of the DOM of the client's page with
 * a fresh HTML fragment.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class ElementReplacement extends DOMModification {
	public static final String ELEMENT_REPLACEMENT_XSI_TYPE = "ElementReplacement";

	/**
	 * Replace the element identified by the given ID with the given HTML
	 * fragment.
	 */
	public ElementReplacement(String elementID, HTMLFragment fragment) {
		super(elementID, fragment);
	}

	@Override
	protected String getXSIType() {
		return ELEMENT_REPLACEMENT_XSI_TYPE;
	}

}
