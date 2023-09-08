/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.graph.layouter.algorithm.ordering;

import java.util.Map;

import com.top_logic.graph.layouter.model.LayoutGraph;
import com.top_logic.graph.layouter.model.LayoutGraph.LayoutNode;
import com.top_logic.graph.layouter.model.layer.DefaultAlternatingLayer;
import com.top_logic.graph.layouter.model.layer.UnorderedNodeLayer;

/**
 * Algorithm to find a {@link LayoutNode} ordering for a given layered {@link LayoutGraph}.
 *
 * @author <a href="mailto:sfo@top-logic.com">Sven F�rster</a>
 */
public interface LayerOrderingAlgorithm {
	/**
	 * Ordered {@link LayoutNode} layers.
	 */
	public Map<Integer, DefaultAlternatingLayer> getLayerOrdering(Map<Integer, UnorderedNodeLayer> layering);
}
