/*
 * SPDX-FileCopyrightText: 2013 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package test.com.top_logic.basic.sql.log;

import static test.com.top_logic.basic.sql.log.LoggingDataSourceTestUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.top_logic.basic.sql.LoggingDataSourceProxy;

/**
 * Tests for the {@link LoggingDataSourceProxy} which use statements that cause no changed rows.
 * 
 * @author <a href="mailto:Jan Stolzenburg@top-logic.com">Jan Stolzenburg</a>
 */
public class TestLoggingDataSourceQueries extends TestCase {

	public void testSingleQuery() throws Throwable {
		String sql = "3:Testquery Alpha";

		LoggingDataSourceProxy dataSource = newDataSource();
		Connection connection = dataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet results = statement.executeQuery(sql);
		readAndClose(results);
		connection.commit();
		flush(dataSource);

		assertIdenticalUpdates(1, 3, sql, dataSource);
	}

	public void testSingleQueryPrepared() throws Throwable {
		String sql = "3:Testquery Alpha";
		
		LoggingDataSourceProxy dataSource = newDataSource();
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet results = statement.executeQuery();
		readAndClose(results);
		connection.commit();
		flush(dataSource);
		
		assertIdenticalUpdates(1, 3, sql, dataSource);
	}
	
	public void testIdenticalQueries() throws Throwable {
		int updates = 3;
		String sql = "3:Testquery Alpha";

		LoggingDataSourceProxy dataSource = newDataSource();
		Connection connection = dataSource.getConnection();
		Statement statement = connection.createStatement();
		for (int i = 0; i < updates; i++) {
			ResultSet results = statement.executeQuery(sql);
			readAndClose(results);
		}
		connection.commit();
		flush(dataSource);

		assertIdenticalUpdates(updates, 9, sql, dataSource);
	}

	public void testIdenticalQueriesPrepare() throws Throwable {
		int updates = 3;
		String sql = "3:Testquery Alpha";
		
		LoggingDataSourceProxy dataSource = newDataSource();
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		for (int i = 0; i < updates; i++) {
			ResultSet results = statement.executeQuery();
			readAndClose(results);
		}
		connection.commit();
		flush(dataSource);
		
		assertIdenticalUpdates(updates, 9, sql, dataSource);
	}

	public void testDistinctQueries() throws Throwable {
		String[] sql = { "2:Testquery Alpha", "3:Testquery Beta", "4:Testquery Gamma" };

		LoggingDataSourceProxy dataSource = newDataSource();
		Connection connection = dataSource.getConnection();
		Statement statement = connection.createStatement();
		for (int i = 0; i < sql.length; i++) {
			ResultSet results = statement.executeQuery(sql[i]);
			readAndClose(results);
		}
		connection.commit();
		flush(dataSource);

		assertDistinctUpdates(new int[] { 1, 1, 1 }, new int[] { 2, 3, 4 }, sql, dataSource);
	}

	/** Entry point for JUnit. Returns a {@link Test} suite containing all the tests of this class. */
	public static Test suite() {
		return new TestSuite(TestLoggingDataSourceQueries.class);
	}

}
