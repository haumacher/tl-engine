/*
 * SPDX-FileCopyrightText: 2022 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package test.com.top_logic.element.meta.benchmark.model.impl;

/**
 * Basic interface for {@link #BENCHMARK_RESULT_TYPE} business objects.
 * 
 * @author Automatically generated by {@link com.top_logic.element.model.generate.InterfaceGenerator}
 */
public interface BenchmarkResultBase extends com.top_logic.model.TLObject {

	/**
	 * Name of type <code>BenchmarkResult</code>
	 */
	String BENCHMARK_RESULT_TYPE = "BenchmarkResult";

	/**
	 * Part <code>millisPerOperation</code> of <code>BenchmarkResult</code>
	 * 
	 * <p>
	 * Declared as <code>tl.core:Double</code> in configuration.
	 * </p>
	 */
	String MILLIS_PER_OPERATION_ATTR = "millisPerOperation";

	/**
	 * Part <code>objectCnt</code> of <code>BenchmarkResult</code>
	 * 
	 * <p>
	 * Declared as <code>tl.core:Long</code> in configuration.
	 * </p>
	 */
	String OBJECT_CNT_ATTR = "objectCnt";

	/**
	 * Part <code>testName</code> of <code>BenchmarkResult</code>
	 * 
	 * <p>
	 * Declared as <code>tl.core:String</code> in configuration.
	 * </p>
	 */
	String TEST_NAME_ATTR = "testName";

	/**
	 * Getter for part {@link #MILLIS_PER_OPERATION_ATTR}.
	 */
	default Double getMillisPerOperation() {
		return (Double) tValueByName(MILLIS_PER_OPERATION_ATTR);
	}

	/**
	 * Setter for part {@link #MILLIS_PER_OPERATION_ATTR}.
	 */
	default void setMillisPerOperation(Double newValue) {
		tUpdateByName(MILLIS_PER_OPERATION_ATTR, newValue);
	}

	/**
	 * Getter for part {@link #OBJECT_CNT_ATTR}.
	 */
	default Long getObjectCnt() {
		return (Long) tValueByName(OBJECT_CNT_ATTR);
	}

	/**
	 * Setter for part {@link #OBJECT_CNT_ATTR}.
	 */
	default void setObjectCnt(Long newValue) {
		tUpdateByName(OBJECT_CNT_ATTR, newValue);
	}

	/**
	 * Getter for part {@link #TEST_NAME_ATTR}.
	 */
	default String getTestName() {
		return (String) tValueByName(TEST_NAME_ATTR);
	}

	/**
	 * Setter for part {@link #TEST_NAME_ATTR}.
	 */
	default void setTestName(String newValue) {
		tUpdateByName(TEST_NAME_ATTR, newValue);
	}

}
