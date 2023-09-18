/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.form.definition;

import com.top_logic.model.form.ReactiveFormCSS;

/**
 * Decision about where to place labels in forms.
 * 
 * @author <a href="mailto:iwi@top-logic.com">Isabell Wittich</a>
 */
public enum LabelPlacement {
	/** No special configuration for this form. */
	DEFAULT,
	/** Label is above its input */
	ABOVE,
	/** Label is in the same line as its input */
	IN_FRONT_OF_INPUT;

	/** Returns whether the element is marked to render the label above. */
	public Boolean getLabelAbove() {
		switch (this) {
			case ABOVE:
				return true;
			case IN_FRONT_OF_INPUT:
				return false;
			default:
				return null;
		}
	}

	/**
	 * CSS class representing this {@link LabelPlacement}.
	 * 
	 * @return May be <code>null</code>.
	 */
	public String cssClass() {
		switch (this) {
			case ABOVE:
				return ReactiveFormCSS.RF_LABEL_ABOVE;
			case IN_FRONT_OF_INPUT:
				return ReactiveFormCSS.RF_LABEL_IN_FRONT_OF_INPUT;
			default:
				return null;
		}
	}
}
