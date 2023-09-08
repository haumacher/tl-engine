/*
 * SPDX-FileCopyrightText: 2015 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.scripting.recorder.ref.value;

import com.top_logic.layout.scripting.recorder.ref.ModelName;


/**
 * A {@link ValueRef} for a tree node.
 * 
 * @since 5.7.5
 * 
 * @deprecated See {@link ValueRef}
 * 
 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
 */
@Deprecated
public interface TreeNodeRef extends ValueRef {

	/**
	 * Setter for {@link #getContext()}.
	 */
	void setContext(ModelName referenceValue);

	/**
	 * The context to resolve the given {@link #getNode() node}.
	 */
	ModelName getContext();

	/**
	 * Setter for {@link #getNode()}.
	 */
	void setNode(ModelName referenceValue);

	/**
	 * The representation of the tree node.
	 */
	ModelName getNode();

}

