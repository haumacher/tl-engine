/*
 * SPDX-FileCopyrightText: 2010 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package test.com.top_logic.layout.table.renderer;

import junit.framework.Test;
import test.com.top_logic.basic.BasicTestCase;
import test.com.top_logic.basic.TestUtils;

import com.top_logic.basic.LogProtocol;

/**
 * All tests in this package.
 * 
 * @author Automatically generated by {@link test.com.top_logic.basic.TestAllGenerator.TestAllTemplate}
 */
public final class TestAll {

	/**
	 * Creates a test containing all tests in package 'test.com.top_logic.layout.table.model' and all subpackages.
	 */
	public static Test suite() {
		final String testPackageName = TestAll.class.getPackage().getName();
		final LogProtocol log = new LogProtocol(TestAll.class);
		final Test allTests = BasicTestCase.createTests(testPackageName, log, true);

		// merge tests to avoid multiple unnecessary setups
		TestUtils.rearrange(allTests);

		return allTests;
	}

}
