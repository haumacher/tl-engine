/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.graph.layouter.model.layer;

import java.util.List;

/**
 * Ordered {@link NodeLayering}.
 *
 * @author <a href="mailto:sfo@top-logic.com">Sven F�rster</a>
 */
public class OrderedNodeLayering extends NodeLayering<OrderedNodeLayer> {

	/**
	 * Creates an ordered {@link NodeLayering}.
	 */
	public OrderedNodeLayering(List<OrderedNodeLayer> layering) {
		super(layering);
	}

}
