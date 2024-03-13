/*
 * SPDX-FileCopyrightText: 2024 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */

package com.top_logic.element.model.migration.model;

import org.w3c.dom.Document;

import com.top_logic.basic.Log;
import com.top_logic.basic.sql.PooledConnection;
import com.top_logic.knowledge.service.migration.MigrationContext;
import com.top_logic.knowledge.service.migration.MigrationProcessor;
import com.top_logic.model.TLModel;

/**
 * {@link MigrationProcessor} that needs to adapt the stored {@link TLModel} base line.
 * 
 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
 */
public interface TLModelBaseLineMigrationProcessor extends MigrationProcessor {

	@Override
	default void doMigration(MigrationContext context, Log log, PooledConnection connection) {
		MigrationUtils.modifyTLModel(log, connection, document -> migrateTLModel(context, log, connection, document));
	}

	/**
	 * Migrates the persistent {@link TLModel} (or parts of it) in the database and adapts the
	 * stored model base line.
	 * 
	 * @param context
	 *        The migration context.
	 * @param log
	 *        The {@link Log} to log informations and potential errors to.
	 * @param connection
	 *        The connection to the database to modify
	 * @param tlModel
	 *        The parsed model base line from the database.
	 * 
	 * @return Whether the given {@link Document model} has been adapted and needs to be written to
	 *         the database.
	 */
	boolean migrateTLModel(MigrationContext context, Log log, PooledConnection connection, Document tlModel);

}

