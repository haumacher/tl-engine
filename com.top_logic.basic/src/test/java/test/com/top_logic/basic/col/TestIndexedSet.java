/*
 * SPDX-FileCopyrightText: 2013 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package test.com.top_logic.basic.col;

import java.util.Collection;
import java.util.Map;

import com.top_logic.basic.col.IndexedSet;
import com.top_logic.basic.col.Mapping;

/**
 * Test case for {@link IndexedSet}
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class TestIndexedSet extends AbstractIndexedCollectionTest<Collection<AbstractIndexedCollectionTest.A>> {

	@Override
	protected Collection<A> createIndexedCollection(Map<String, A> indexMap, Mapping<A, String> keyMapping) {
		return new IndexedSet<>(indexMap, keyMapping);
	}

}
