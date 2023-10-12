package com.top_logic.model.search.expr.config.operations.binary;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;

import com.top_logic.basic.config.ConfigurationException;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.basic.io.binary.BinaryDataSource;
import com.top_logic.element.meta.TypeSpec;
import com.top_logic.model.TLType;
import com.top_logic.model.search.expr.GenericMethod;
import com.top_logic.model.search.expr.I18NConstants;
import com.top_logic.model.search.expr.SearchExpression;
import com.top_logic.model.search.expr.SimpleGenericMethod;
import com.top_logic.model.search.expr.config.dom.Expr;
import com.top_logic.model.search.expr.config.operations.AbstractSimpleMethodBuilder;
import com.top_logic.model.search.expr.config.operations.ArgumentDescriptor;
import com.top_logic.model.search.expr.config.operations.MethodBuilder;
import com.top_logic.model.util.TLModelUtil;
import com.top_logic.util.error.TopLogicException;

/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */

/**
 * {@link GenericMethod} encoding a binary value into a string.
 *
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class Base64Encode extends SimpleGenericMethod {

	/** 
	 * Creates a {@link Base64Encode}.
	 */
	protected Base64Encode(String name, SearchExpression self, SearchExpression[] arguments) {
		super(name, self, arguments);
	}

	@Override
	public GenericMethod copy(SearchExpression self, SearchExpression[] arguments) {
		return new Base64Encode(getName(), self, arguments);
	}

	@Override
	public TLType getType(TLType selfType, List<TLType> argumentTypes) {
		return TLModelUtil.findType(TypeSpec.STRING_TYPE);
	}

	@Override
	public Object eval(Object self, Object[] arguments) {
		if (self == null) {
			return null;
		}

		if (self instanceof BinaryDataSource) {
			Encoder encoding = asBoolean(arguments[0]) ? Base64.getMimeEncoder() : Base64.getEncoder();

			OutputStream buffer = new ASCIIBuffer();
			try (OutputStream encoder = encoding.wrap(buffer)) {
				((BinaryDataSource) self).deliverTo(encoder);
			} catch (IOException ex) {
				throw new TopLogicException(I18NConstants.ENCODING_FAILED__MSG_EXPR.fill(ex.getMessage(), getSelf()));
			}
			return buffer.toString();
		} else {
			throw new TopLogicException(I18NConstants.ERROR_NOT_A_BINARY_VALUE__VAL_EXPR.fill(self, getSelf()));
		}
	}

	/**
	 * String buffer accepting binary data in ASCII encoding scheme.
	 */
	private static final class ASCIIBuffer extends OutputStream {
		private final StringBuilder _buffer = new StringBuilder();

		@Override
		public void write(int b) throws IOException {
			_buffer.append((char) ((byte) b));
		}

		@Override
		public String toString() {
			return _buffer.toString();
		}
	}

	/**
	 * {@link MethodBuilder} for {@link Base64Encode}.
	 */
	public static class Builder extends AbstractSimpleMethodBuilder<Base64Encode> {

		private static final ArgumentDescriptor DESCRIPTOR = ArgumentDescriptor.builder()
			.optional("mime", false)
			.build();

		/**
		 * Creates a {@link Builder}.
		 */
		public Builder(InstantiationContext context, Config<?> config) {
			super(context, config);
		}

		@Override
		public Base64Encode build(Expr expr, SearchExpression self, SearchExpression[] args) throws ConfigurationException {
			return new Base64Encode(getName(), self, args);
		}

		@Override
		public ArgumentDescriptor descriptor() {
			return DESCRIPTOR;
		}

	}
}
