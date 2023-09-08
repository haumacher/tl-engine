/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.graph.layouter.algorithm.acycle;

import com.top_logic.graph.layouter.model.LayoutGraph;

/**
 * Algorithm to find for the given graph the maximal acyclic subgraph.
 *
 * @author <a href="mailto:sfo@top-logic.com">Sven F�rster</a>
 */
public interface AcycleAlgorithm {

	/**
	 * Maximal acyclic subgraph.
	 */
	public LayoutGraph findMaximalAcyclicSubgraph(LayoutGraph graph);
}
