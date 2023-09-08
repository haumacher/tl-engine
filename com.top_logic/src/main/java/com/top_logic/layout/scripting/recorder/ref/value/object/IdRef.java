/*
 * SPDX-FileCopyrightText: 2009 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.scripting.recorder.ref.value.object;

import com.top_logic.basic.TLID;
import com.top_logic.dob.meta.MOClass;
import com.top_logic.layout.scripting.recorder.ref.value.ValueRef;

/**
 * A reference to an object by table name and identifier.
 * 
 * @deprecated See {@link ValueRef}
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
@Deprecated
public interface IdRef extends VersionedObjectRef {

	/**
	 * The name of the {@link MOClass}.
	 */
	String getTableName();
	void setTableName(String value);

	/**
	 * The object identifier.
	 */
	TLID getObjectName();
	void setObjectName(TLID value);
}
