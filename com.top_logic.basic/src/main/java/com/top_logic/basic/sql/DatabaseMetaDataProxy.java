/*
 * SPDX-FileCopyrightText: 2013 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.basic.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;


/**
 * Abstract proxy for {@link DatabaseMetaData}.
 * 
 * @see #impl()
 * 
 * @author Automatically generated by <code>com.top_logic.basic.generate.ProxyGenerator</code>
 */
public abstract class DatabaseMetaDataProxy implements DatabaseMetaData{

	/**
	 * The underlying implementation.
	 */
	protected abstract DatabaseMetaData impl();

	@Override
	public String getURL() throws SQLException {
		return impl().getURL();
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		return impl().isReadOnly();
	}

	@Override
	public ResultSet getAttributes(String a1, String a2, String a3, String a4) throws SQLException {
		return impl().getAttributes(a1, a2, a3, a4);
	}

	@Override
	public boolean allProceduresAreCallable() throws SQLException {
		return impl().allProceduresAreCallable();
	}

	@Override
	public boolean allTablesAreSelectable() throws SQLException {
		return impl().allTablesAreSelectable();
	}

	@Override
	public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
		return impl().autoCommitFailureClosesAllResultSets();
	}

	@Override
	public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
		return impl().dataDefinitionCausesTransactionCommit();
	}

	@Override
	public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
		return impl().dataDefinitionIgnoredInTransactions();
	}

	@Override
	public boolean deletesAreDetected(int a1) throws SQLException {
		return impl().deletesAreDetected(a1);
	}

	@Override
	public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
		return impl().doesMaxRowSizeIncludeBlobs();
	}

	@Override
	public ResultSet getBestRowIdentifier(String a1, String a2, String a3, int a4, boolean a5) throws SQLException {
		return impl().getBestRowIdentifier(a1, a2, a3, a4, a5);
	}

	@Override
	public String getCatalogSeparator() throws SQLException {
		return impl().getCatalogSeparator();
	}

	@Override
	public String getCatalogTerm() throws SQLException {
		return impl().getCatalogTerm();
	}

	@Override
	public ResultSet getCatalogs() throws SQLException {
		return impl().getCatalogs();
	}

	@Override
	public ResultSet getClientInfoProperties() throws SQLException {
		return impl().getClientInfoProperties();
	}

	@Override
	public ResultSet getColumnPrivileges(String a1, String a2, String a3, String a4) throws SQLException {
		return impl().getColumnPrivileges(a1, a2, a3, a4);
	}

	@Override
	public ResultSet getColumns(String a1, String a2, String a3, String a4) throws SQLException {
		return impl().getColumns(a1, a2, a3, a4);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return impl().getConnection();
	}

	@Override
	public ResultSet getCrossReference(String a1, String a2, String a3, String a4, String a5, String a6) throws SQLException {
		return impl().getCrossReference(a1, a2, a3, a4, a5, a6);
	}

	@Override
	public int getDatabaseMajorVersion() throws SQLException {
		return impl().getDatabaseMajorVersion();
	}

	@Override
	public int getDatabaseMinorVersion() throws SQLException {
		return impl().getDatabaseMinorVersion();
	}

	@Override
	public String getDatabaseProductName() throws SQLException {
		return impl().getDatabaseProductName();
	}

	@Override
	public String getDatabaseProductVersion() throws SQLException {
		return impl().getDatabaseProductVersion();
	}

	@Override
	public int getDefaultTransactionIsolation() throws SQLException {
		return impl().getDefaultTransactionIsolation();
	}

	@Override
	public int getDriverMajorVersion() {
		return impl().getDriverMajorVersion();
	}

	@Override
	public int getDriverMinorVersion() {
		return impl().getDriverMinorVersion();
	}

	@Override
	public String getDriverName() throws SQLException {
		return impl().getDriverName();
	}

	@Override
	public String getDriverVersion() throws SQLException {
		return impl().getDriverVersion();
	}

	@Override
	public ResultSet getExportedKeys(String a1, String a2, String a3) throws SQLException {
		return impl().getExportedKeys(a1, a2, a3);
	}

	@Override
	public String getExtraNameCharacters() throws SQLException {
		return impl().getExtraNameCharacters();
	}

	@Override
	public ResultSet getFunctionColumns(String a1, String a2, String a3, String a4) throws SQLException {
		return impl().getFunctionColumns(a1, a2, a3, a4);
	}

	@Override
	public ResultSet getFunctions(String a1, String a2, String a3) throws SQLException {
		return impl().getFunctions(a1, a2, a3);
	}

	@Override
	public String getIdentifierQuoteString() throws SQLException {
		return impl().getIdentifierQuoteString();
	}

	@Override
	public ResultSet getImportedKeys(String a1, String a2, String a3) throws SQLException {
		return impl().getImportedKeys(a1, a2, a3);
	}

	@Override
	public ResultSet getIndexInfo(String a1, String a2, String a3, boolean a4, boolean a5) throws SQLException {
		return impl().getIndexInfo(a1, a2, a3, a4, a5);
	}

	@Override
	public int getJDBCMajorVersion() throws SQLException {
		return impl().getJDBCMajorVersion();
	}

	@Override
	public int getJDBCMinorVersion() throws SQLException {
		return impl().getJDBCMinorVersion();
	}

	@Override
	public int getMaxBinaryLiteralLength() throws SQLException {
		return impl().getMaxBinaryLiteralLength();
	}

	@Override
	public int getMaxCatalogNameLength() throws SQLException {
		return impl().getMaxCatalogNameLength();
	}

	@Override
	public int getMaxCharLiteralLength() throws SQLException {
		return impl().getMaxCharLiteralLength();
	}

	@Override
	public int getMaxColumnNameLength() throws SQLException {
		return impl().getMaxColumnNameLength();
	}

	@Override
	public int getMaxColumnsInGroupBy() throws SQLException {
		return impl().getMaxColumnsInGroupBy();
	}

	@Override
	public int getMaxColumnsInIndex() throws SQLException {
		return impl().getMaxColumnsInIndex();
	}

	@Override
	public int getMaxColumnsInOrderBy() throws SQLException {
		return impl().getMaxColumnsInOrderBy();
	}

	@Override
	public int getMaxColumnsInSelect() throws SQLException {
		return impl().getMaxColumnsInSelect();
	}

	@Override
	public int getMaxColumnsInTable() throws SQLException {
		return impl().getMaxColumnsInTable();
	}

	@Override
	public int getMaxConnections() throws SQLException {
		return impl().getMaxConnections();
	}

	@Override
	public int getMaxCursorNameLength() throws SQLException {
		return impl().getMaxCursorNameLength();
	}

	@Override
	public int getMaxIndexLength() throws SQLException {
		return impl().getMaxIndexLength();
	}

	@Override
	public int getMaxProcedureNameLength() throws SQLException {
		return impl().getMaxProcedureNameLength();
	}

	@Override
	public int getMaxRowSize() throws SQLException {
		return impl().getMaxRowSize();
	}

	@Override
	public int getMaxSchemaNameLength() throws SQLException {
		return impl().getMaxSchemaNameLength();
	}

	@Override
	public int getMaxStatementLength() throws SQLException {
		return impl().getMaxStatementLength();
	}

	@Override
	public int getMaxStatements() throws SQLException {
		return impl().getMaxStatements();
	}

	@Override
	public int getMaxTableNameLength() throws SQLException {
		return impl().getMaxTableNameLength();
	}

	@Override
	public int getMaxTablesInSelect() throws SQLException {
		return impl().getMaxTablesInSelect();
	}

	@Override
	public int getMaxUserNameLength() throws SQLException {
		return impl().getMaxUserNameLength();
	}

	@Override
	public String getNumericFunctions() throws SQLException {
		return impl().getNumericFunctions();
	}

	@Override
	public ResultSet getPrimaryKeys(String a1, String a2, String a3) throws SQLException {
		return impl().getPrimaryKeys(a1, a2, a3);
	}

	@Override
	public ResultSet getProcedureColumns(String a1, String a2, String a3, String a4) throws SQLException {
		return impl().getProcedureColumns(a1, a2, a3, a4);
	}

	@Override
	public String getProcedureTerm() throws SQLException {
		return impl().getProcedureTerm();
	}

	@Override
	public ResultSet getProcedures(String a1, String a2, String a3) throws SQLException {
		return impl().getProcedures(a1, a2, a3);
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		return impl().getResultSetHoldability();
	}

	@Override
	public RowIdLifetime getRowIdLifetime() throws SQLException {
		return impl().getRowIdLifetime();
	}

	@Override
	public String getSQLKeywords() throws SQLException {
		return impl().getSQLKeywords();
	}

	@Override
	public int getSQLStateType() throws SQLException {
		return impl().getSQLStateType();
	}

	@Override
	public String getSchemaTerm() throws SQLException {
		return impl().getSchemaTerm();
	}

	@Override
	public ResultSet getSchemas(String a1, String a2) throws SQLException {
		return impl().getSchemas(a1, a2);
	}

	@Override
	public ResultSet getSchemas() throws SQLException {
		return impl().getSchemas();
	}

	@Override
	public String getSearchStringEscape() throws SQLException {
		return impl().getSearchStringEscape();
	}

	@Override
	public String getStringFunctions() throws SQLException {
		return impl().getStringFunctions();
	}

	@Override
	public ResultSet getSuperTables(String a1, String a2, String a3) throws SQLException {
		return impl().getSuperTables(a1, a2, a3);
	}

	@Override
	public ResultSet getSuperTypes(String a1, String a2, String a3) throws SQLException {
		return impl().getSuperTypes(a1, a2, a3);
	}

	@Override
	public String getSystemFunctions() throws SQLException {
		return impl().getSystemFunctions();
	}

	@Override
	public ResultSet getTablePrivileges(String a1, String a2, String a3) throws SQLException {
		return impl().getTablePrivileges(a1, a2, a3);
	}

	@Override
	public ResultSet getTableTypes() throws SQLException {
		return impl().getTableTypes();
	}

	@Override
	public ResultSet getTables(String a1, String a2, String a3, String[] a4) throws SQLException {
		return impl().getTables(a1, a2, a3, a4);
	}

	@Override
	public String getTimeDateFunctions() throws SQLException {
		return impl().getTimeDateFunctions();
	}

	@Override
	public ResultSet getTypeInfo() throws SQLException {
		return impl().getTypeInfo();
	}

	@Override
	public ResultSet getUDTs(String a1, String a2, String a3, int[] a4) throws SQLException {
		return impl().getUDTs(a1, a2, a3, a4);
	}

	@Override
	public String getUserName() throws SQLException {
		return impl().getUserName();
	}

	@Override
	public ResultSet getVersionColumns(String a1, String a2, String a3) throws SQLException {
		return impl().getVersionColumns(a1, a2, a3);
	}

	@Override
	public boolean insertsAreDetected(int a1) throws SQLException {
		return impl().insertsAreDetected(a1);
	}

	@Override
	public boolean isCatalogAtStart() throws SQLException {
		return impl().isCatalogAtStart();
	}

	@Override
	public boolean locatorsUpdateCopy() throws SQLException {
		return impl().locatorsUpdateCopy();
	}

	@Override
	public boolean nullPlusNonNullIsNull() throws SQLException {
		return impl().nullPlusNonNullIsNull();
	}

	@Override
	public boolean nullsAreSortedAtEnd() throws SQLException {
		return impl().nullsAreSortedAtEnd();
	}

	@Override
	public boolean nullsAreSortedAtStart() throws SQLException {
		return impl().nullsAreSortedAtStart();
	}

	@Override
	public boolean nullsAreSortedHigh() throws SQLException {
		return impl().nullsAreSortedHigh();
	}

	@Override
	public boolean nullsAreSortedLow() throws SQLException {
		return impl().nullsAreSortedLow();
	}

	@Override
	public boolean othersDeletesAreVisible(int a1) throws SQLException {
		return impl().othersDeletesAreVisible(a1);
	}

	@Override
	public boolean othersInsertsAreVisible(int a1) throws SQLException {
		return impl().othersInsertsAreVisible(a1);
	}

	@Override
	public boolean othersUpdatesAreVisible(int a1) throws SQLException {
		return impl().othersUpdatesAreVisible(a1);
	}

	@Override
	public boolean ownDeletesAreVisible(int a1) throws SQLException {
		return impl().ownDeletesAreVisible(a1);
	}

	@Override
	public boolean ownInsertsAreVisible(int a1) throws SQLException {
		return impl().ownInsertsAreVisible(a1);
	}

	@Override
	public boolean ownUpdatesAreVisible(int a1) throws SQLException {
		return impl().ownUpdatesAreVisible(a1);
	}

	@Override
	public boolean storesLowerCaseIdentifiers() throws SQLException {
		return impl().storesLowerCaseIdentifiers();
	}

	@Override
	public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
		return impl().storesLowerCaseQuotedIdentifiers();
	}

	@Override
	public boolean storesMixedCaseIdentifiers() throws SQLException {
		return impl().storesMixedCaseIdentifiers();
	}

	@Override
	public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
		return impl().storesMixedCaseQuotedIdentifiers();
	}

	@Override
	public boolean storesUpperCaseIdentifiers() throws SQLException {
		return impl().storesUpperCaseIdentifiers();
	}

	@Override
	public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
		return impl().storesUpperCaseQuotedIdentifiers();
	}

	@Override
	public boolean supportsANSI92EntryLevelSQL() throws SQLException {
		return impl().supportsANSI92EntryLevelSQL();
	}

	@Override
	public boolean supportsANSI92FullSQL() throws SQLException {
		return impl().supportsANSI92FullSQL();
	}

	@Override
	public boolean supportsANSI92IntermediateSQL() throws SQLException {
		return impl().supportsANSI92IntermediateSQL();
	}

	@Override
	public boolean supportsAlterTableWithAddColumn() throws SQLException {
		return impl().supportsAlterTableWithAddColumn();
	}

	@Override
	public boolean supportsAlterTableWithDropColumn() throws SQLException {
		return impl().supportsAlterTableWithDropColumn();
	}

	@Override
	public boolean supportsBatchUpdates() throws SQLException {
		return impl().supportsBatchUpdates();
	}

	@Override
	public boolean supportsCatalogsInDataManipulation() throws SQLException {
		return impl().supportsCatalogsInDataManipulation();
	}

	@Override
	public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
		return impl().supportsCatalogsInIndexDefinitions();
	}

	@Override
	public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
		return impl().supportsCatalogsInPrivilegeDefinitions();
	}

	@Override
	public boolean supportsCatalogsInProcedureCalls() throws SQLException {
		return impl().supportsCatalogsInProcedureCalls();
	}

	@Override
	public boolean supportsCatalogsInTableDefinitions() throws SQLException {
		return impl().supportsCatalogsInTableDefinitions();
	}

	@Override
	public boolean supportsColumnAliasing() throws SQLException {
		return impl().supportsColumnAliasing();
	}

	@Override
	public boolean supportsConvert(int a1, int a2) throws SQLException {
		return impl().supportsConvert(a1, a2);
	}

	@Override
	public boolean supportsConvert() throws SQLException {
		return impl().supportsConvert();
	}

	@Override
	public boolean supportsCoreSQLGrammar() throws SQLException {
		return impl().supportsCoreSQLGrammar();
	}

	@Override
	public boolean supportsCorrelatedSubqueries() throws SQLException {
		return impl().supportsCorrelatedSubqueries();
	}

	@Override
	public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
		return impl().supportsDataDefinitionAndDataManipulationTransactions();
	}

	@Override
	public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
		return impl().supportsDataManipulationTransactionsOnly();
	}

	@Override
	public boolean supportsDifferentTableCorrelationNames() throws SQLException {
		return impl().supportsDifferentTableCorrelationNames();
	}

	@Override
	public boolean supportsExpressionsInOrderBy() throws SQLException {
		return impl().supportsExpressionsInOrderBy();
	}

	@Override
	public boolean supportsExtendedSQLGrammar() throws SQLException {
		return impl().supportsExtendedSQLGrammar();
	}

	@Override
	public boolean supportsFullOuterJoins() throws SQLException {
		return impl().supportsFullOuterJoins();
	}

	@Override
	public boolean supportsGetGeneratedKeys() throws SQLException {
		return impl().supportsGetGeneratedKeys();
	}

	@Override
	public boolean supportsGroupBy() throws SQLException {
		return impl().supportsGroupBy();
	}

	@Override
	public boolean supportsGroupByBeyondSelect() throws SQLException {
		return impl().supportsGroupByBeyondSelect();
	}

	@Override
	public boolean supportsGroupByUnrelated() throws SQLException {
		return impl().supportsGroupByUnrelated();
	}

	@Override
	public boolean supportsIntegrityEnhancementFacility() throws SQLException {
		return impl().supportsIntegrityEnhancementFacility();
	}

	@Override
	public boolean supportsLikeEscapeClause() throws SQLException {
		return impl().supportsLikeEscapeClause();
	}

	@Override
	public boolean supportsLimitedOuterJoins() throws SQLException {
		return impl().supportsLimitedOuterJoins();
	}

	@Override
	public boolean supportsMinimumSQLGrammar() throws SQLException {
		return impl().supportsMinimumSQLGrammar();
	}

	@Override
	public boolean supportsMixedCaseIdentifiers() throws SQLException {
		return impl().supportsMixedCaseIdentifiers();
	}

	@Override
	public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
		return impl().supportsMixedCaseQuotedIdentifiers();
	}

	@Override
	public boolean supportsMultipleOpenResults() throws SQLException {
		return impl().supportsMultipleOpenResults();
	}

	@Override
	public boolean supportsMultipleResultSets() throws SQLException {
		return impl().supportsMultipleResultSets();
	}

	@Override
	public boolean supportsMultipleTransactions() throws SQLException {
		return impl().supportsMultipleTransactions();
	}

	@Override
	public boolean supportsNamedParameters() throws SQLException {
		return impl().supportsNamedParameters();
	}

	@Override
	public boolean supportsNonNullableColumns() throws SQLException {
		return impl().supportsNonNullableColumns();
	}

	@Override
	public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
		return impl().supportsOpenCursorsAcrossCommit();
	}

	@Override
	public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
		return impl().supportsOpenCursorsAcrossRollback();
	}

	@Override
	public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
		return impl().supportsOpenStatementsAcrossCommit();
	}

	@Override
	public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
		return impl().supportsOpenStatementsAcrossRollback();
	}

	@Override
	public boolean supportsOrderByUnrelated() throws SQLException {
		return impl().supportsOrderByUnrelated();
	}

	@Override
	public boolean supportsOuterJoins() throws SQLException {
		return impl().supportsOuterJoins();
	}

	@Override
	public boolean supportsPositionedDelete() throws SQLException {
		return impl().supportsPositionedDelete();
	}

	@Override
	public boolean supportsPositionedUpdate() throws SQLException {
		return impl().supportsPositionedUpdate();
	}

	@Override
	public boolean supportsResultSetConcurrency(int a1, int a2) throws SQLException {
		return impl().supportsResultSetConcurrency(a1, a2);
	}

	@Override
	public boolean supportsResultSetHoldability(int a1) throws SQLException {
		return impl().supportsResultSetHoldability(a1);
	}

	@Override
	public boolean supportsResultSetType(int a1) throws SQLException {
		return impl().supportsResultSetType(a1);
	}

	@Override
	public boolean supportsSavepoints() throws SQLException {
		return impl().supportsSavepoints();
	}

	@Override
	public boolean supportsSchemasInDataManipulation() throws SQLException {
		return impl().supportsSchemasInDataManipulation();
	}

	@Override
	public boolean supportsSchemasInIndexDefinitions() throws SQLException {
		return impl().supportsSchemasInIndexDefinitions();
	}

	@Override
	public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
		return impl().supportsSchemasInPrivilegeDefinitions();
	}

	@Override
	public boolean supportsSchemasInProcedureCalls() throws SQLException {
		return impl().supportsSchemasInProcedureCalls();
	}

	@Override
	public boolean supportsSchemasInTableDefinitions() throws SQLException {
		return impl().supportsSchemasInTableDefinitions();
	}

	@Override
	public boolean supportsSelectForUpdate() throws SQLException {
		return impl().supportsSelectForUpdate();
	}

	@Override
	public boolean supportsStatementPooling() throws SQLException {
		return impl().supportsStatementPooling();
	}

	@Override
	public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
		return impl().supportsStoredFunctionsUsingCallSyntax();
	}

	@Override
	public boolean supportsStoredProcedures() throws SQLException {
		return impl().supportsStoredProcedures();
	}

	@Override
	public boolean supportsSubqueriesInComparisons() throws SQLException {
		return impl().supportsSubqueriesInComparisons();
	}

	@Override
	public boolean supportsSubqueriesInExists() throws SQLException {
		return impl().supportsSubqueriesInExists();
	}

	@Override
	public boolean supportsSubqueriesInIns() throws SQLException {
		return impl().supportsSubqueriesInIns();
	}

	@Override
	public boolean supportsSubqueriesInQuantifieds() throws SQLException {
		return impl().supportsSubqueriesInQuantifieds();
	}

	@Override
	public boolean supportsTableCorrelationNames() throws SQLException {
		return impl().supportsTableCorrelationNames();
	}

	@Override
	public boolean supportsTransactionIsolationLevel(int a1) throws SQLException {
		return impl().supportsTransactionIsolationLevel(a1);
	}

	@Override
	public boolean supportsTransactions() throws SQLException {
		return impl().supportsTransactions();
	}

	@Override
	public boolean supportsUnion() throws SQLException {
		return impl().supportsUnion();
	}

	@Override
	public boolean supportsUnionAll() throws SQLException {
		return impl().supportsUnionAll();
	}

	@Override
	public boolean updatesAreDetected(int a1) throws SQLException {
		return impl().updatesAreDetected(a1);
	}

	@Override
	public boolean usesLocalFilePerTable() throws SQLException {
		return impl().usesLocalFilePerTable();
	}

	@Override
	public boolean usesLocalFiles() throws SQLException {
		return impl().usesLocalFiles();
	}

	@Override
	public boolean isWrapperFor(Class<?> a1) throws SQLException {
		return impl().isWrapperFor(a1);
	}

	@Override
	public <T> T unwrap(Class<T> a1) throws SQLException {
		return impl().unwrap(a1);
	}

	@Override
	public ResultSet getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern,
			String columnNamePattern) throws SQLException {
		return impl().getPseudoColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
	}

	@Override
	public boolean generatedKeyAlwaysReturned() throws SQLException {
		return impl().generatedKeyAlwaysReturned();
	}

}
