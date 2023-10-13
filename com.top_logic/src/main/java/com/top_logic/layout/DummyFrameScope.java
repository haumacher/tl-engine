/*
 * SPDX-FileCopyrightText: 2016 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import com.top_logic.base.services.simpleajax.ClientAction;
import com.top_logic.model.listen.ModelScope;

/**
 * {@link FrameScope} for testing and one-time rendering of exports.
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public final class DummyFrameScope implements FrameScope {

	private int _id = 1;

	@Override
	public boolean removeCommandListener(CommandListener listener) {
		return false;
	}

	@Override
	public Collection<CommandListener> getCommandListener() {
		return Collections.emptyList();
	}

	@Override
	public CommandListener getCommandListener(String id) {
		return null;
	}

	@Override
	public void clear() {
		// Ignore.
	}

	@Override
	public void addCommandListener(CommandListener listener) {
		// Ignore.
	}

	@Override
	public void handleContent(DisplayContext context, String id, URLParser url) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void registerContentHandler(String id, ContentHandler handler) {
		// Ignore.
	}

	@Override
	public Collection<? extends ContentHandler> inspectSubHandlers() {
		return Collections.emptyList();
	}

	@Override
	public URLBuilder getURL(DisplayContext context, ContentHandler handler) {
		throw new UnsupportedOperationException();
	}

	@Override
	public URLBuilder getURL(DisplayContext context) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean deregisterContentHandler(ContentHandler handler) {
		return false;
	}

	@Override
	public String createNewID() {
		return "c" + (_id++);
	}

	@Override
	public ModelScope getModelScope() {
		return null;
	}

	@Override
	public WindowScope getWindowScope() {
		return null;
	}

	@Override
	public FrameScope getEnclosingScope() {
		return null;
	}

	@Override
	public <T extends Appendable> T appendClientReference(T out) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addClientAction(ClientAction update) {
		// Ignore.
	}
}