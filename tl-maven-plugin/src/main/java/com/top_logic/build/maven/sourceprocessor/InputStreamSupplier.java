/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.build.maven.sourceprocessor;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

/**
 * Like {@link Supplier}, but allow to throw {@link IOException}.
 *
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public interface InputStreamSupplier {

	InputStream get() throws IOException;

}
