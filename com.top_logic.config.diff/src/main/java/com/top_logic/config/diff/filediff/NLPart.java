/*
 * SPDX-FileCopyrightText: 2011 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.config.diff.filediff;

import com.top_logic.config.diff.google.diff_match_patch.Operation;

/**
 * {@link LinePart} that represents a line terminator.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class NLPart extends LinePart {

	public NLPart(String text, Operation operation, int diffIndex) {
		super(text, operation, diffIndex);
	}

	@Override
	public boolean isNL() {
		return true;
	}

}
