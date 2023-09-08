/*
 * SPDX-FileCopyrightText: 2009 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.knowledge.service.db2.expr.sym;


/**
 * {@link Symbol} for the <code>null</code> value.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class NullSymbol extends AbstractSymbol {

	/**
	 * Singleton {@link NullSymbol} instance.
	 */
	public static final NullSymbol INSTANCE = new NullSymbol();

	private NullSymbol() {
		super(null);
	}

	@Override
	public Symbol getParent() {
		return null;
	}

	@Override
	public void initParent(TupleSymbol tupleSymbol) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <R, A> R visit(SymbolVisitor<R, A> v, A arg) {
		return v.visitNullSymbol(this, arg);
	}

}
