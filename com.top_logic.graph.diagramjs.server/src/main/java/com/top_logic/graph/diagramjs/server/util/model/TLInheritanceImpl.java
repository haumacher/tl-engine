/*
 * SPDX-FileCopyrightText: 2019 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.graph.diagramjs.server.util.model;

import java.util.Objects;

import com.top_logic.model.TLClass;

/**
 * Implementation of {@link TLInheritance}.
 *
 * @author <a href="mailto:sfo@top-logic.com">Sven F�rster</a>
 */
public class TLInheritanceImpl implements TLInheritance {

	private TLClass _source;

	private TLClass _target;

	/**
	 * Creates a {@link TLInheritance} for the given {@link TLClass}es.
	 */
	public TLInheritanceImpl(TLClass source, TLClass target) {
		_source = source;
		_target = target;
	}

	@Override
	public TLClass getSource() {
		return _source;
	}

	/**
	 * @see #getSource()
	 */
	public void setSource(TLClass source) {
		_source = source;
	}

	@Override
	public TLClass getTarget() {
		return _target;
	}

	/**
	 * @see #getTarget()
	 */
	public void setTarget(TLClass target) {
		_target = target;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_source, _target);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TLInheritanceImpl other = (TLInheritanceImpl) obj;
		return Objects.equals(_source, other._source) && Objects.equals(_target, other._target);
	}

}