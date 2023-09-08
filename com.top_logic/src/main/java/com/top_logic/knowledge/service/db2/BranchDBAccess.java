/*
 * SPDX-FileCopyrightText: 2015 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.knowledge.service.db2;

import com.top_logic.basic.annotation.FrameworkInternal;
import com.top_logic.basic.db.sql.SQLExpression;
import com.top_logic.basic.sql.DBHelper;
import com.top_logic.dob.meta.MORepository;
import com.top_logic.knowledge.service.db2.expr.sym.TableSymbol;

/**
 * {@link DBAccess} to encapsulate access to branch table.
 * 
 * @author <a href=mailto:daniel.busche@top-logic.com>Daniel Busche</a>
 */
@FrameworkInternal
public final class BranchDBAccess extends SystemTypeDBAccess {

	/**
	 * {@link DBAccessFactory} for {@link BranchDBAccess} implementations.
	 */
	@FrameworkInternal
	public static class Factory implements DBAccessFactory {

		/**
		 * Singleton {@link BranchDBAccess.Factory} instance.
		 */
		public static final BranchDBAccess.Factory INSTANCE = new BranchDBAccess.Factory();

		private Factory() {
			// Singleton constructor.
		}

		@Override
		public DBAccess createDBAccess(DBHelper sqlDialect, MOKnowledgeItemImpl type, MORepository repository) {
			return new BranchDBAccess(sqlDialect, type);
		}

	}

	BranchDBAccess(DBHelper sqlDialect, MOKnowledgeItemImpl table) {
		super(sqlDialect, table);
	}

	@Override
	public SQLExpression createRevMinExpr(TableSymbol table) {
		return createColumn(BranchSupport.getCreateRevAttr(type), table);
	}

	@Override
	protected String getIdAttributeName() {
		return BranchSupport.BRANCH_ID_ATTRIBUTE_NAME;
	}

}