/*
 * SPDX-FileCopyrightText: 2013 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package test.com.top_logic.demo.scripted.dialog.clipboard;

import junit.framework.Test;
import junit.framework.TestSuite;

import test.com.top_logic.demo.DemoSetup;
import test.com.top_logic.layout.scripting.XmlScriptedTestUtil;

/**
 * Test for clipboard dialog.
 * 
 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
 */
public class TestClipboardDialog {

	/**
	 * a cumulative {@link Test} for all Tests in {@link TestClipboardDialog}.
	 */
	public static Test suite() {
		String[] testCases = {
			"01_Setup",
			"02_CheckButtonExecutability",
			"FF_Teardown",
		};
		TestSuite testSuite = XmlScriptedTestUtil.suite(TestClipboardDialog.class, testCases);
		return DemoSetup.createDemoSetup(testSuite);
	}
}

