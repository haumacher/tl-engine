/*
 * SPDX-FileCopyrightText: 2017 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.config;

import com.top_logic.basic.config.annotation.Abstract;
import com.top_logic.model.TLModule;
import com.top_logic.model.annotate.TLAnnotation;

/**
 * Base interface for {@link TLAnnotation}s on {@link TLModule}s.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
@Abstract
public interface TLModuleAnnotation extends TLAnnotation {
	// Pure marker interface.
}
