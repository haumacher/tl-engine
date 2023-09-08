/*
 * SPDX-FileCopyrightText: 2018 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.basic.config.format;

import com.top_logic.basic.config.ConfigUtil;
import com.top_logic.basic.config.ConfigurationException;
import com.top_logic.basic.config.ConfigurationValueProvider;

/**
 * {@link ConfigurationValueProvider} for properties of type {@link Byte}.
 *
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public final class ByteWrapperFormat extends PrimitiveValueProvider {

	/**
	 * Singleton {@link ByteWrapperFormat} instance.
	 */
	public static final ByteWrapperFormat INSTANCE = new ByteWrapperFormat();

	/** {@link GenericArrayFormat} for arrays of type {@link Byte}. */
	public static final GenericArrayFormat<Byte[]> ARRAY_FORMAT =
		new GenericArrayFormat<>(Byte[].class, INSTANCE);

	private ByteWrapperFormat() {
		super(Byte.class);
	}

	@Override
	public Object getValueNonEmpty(String propertyName, CharSequence propertyValue) throws ConfigurationException {
		return ConfigUtil.getByte(propertyName, propertyValue);
	}
}