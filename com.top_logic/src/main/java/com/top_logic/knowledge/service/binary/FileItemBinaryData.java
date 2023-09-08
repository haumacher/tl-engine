/*
 * SPDX-FileCopyrightText: 2009 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.knowledge.service.binary;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.fileupload.FileItem;

import com.top_logic.basic.io.binary.AbstractBinaryData;

/**
 * Wrap a {@link FileItem} as BinaryData to avoid unnecessary copying of Uploaded Data.
 * 
 * This class is not aware of changes of the underlying FileItem and therefore is not 
 * immutable.
 * 
 * @author    <a href="mailto:kha@top-logic.com">kha</a>
 */
public class FileItemBinaryData extends AbstractBinaryData {

    /** The Object wrapped by this class */
    FileItem fileItem;

    public FileItemBinaryData(FileItem aFileItem) {
        this.fileItem = aFileItem;
    }
    
    /**
     * @see com.top_logic.basic.io.binary.BinaryData#getSize()
     */
    @Override
	public long getSize() {
        return fileItem.getSize();
    }
    
    /**
     * @see com.top_logic.basic.io.binary.BinaryData#getStream()
     */
    @Override
	public InputStream getStream() throws IOException {
        return fileItem.getInputStream();
    }
    
	@Override
	public String getName() {
		return fileItem.getName();
	}

	@Override
	public String getContentType() {
		return nonNullContentType(fileItem.getContentType());
	}
    
}

