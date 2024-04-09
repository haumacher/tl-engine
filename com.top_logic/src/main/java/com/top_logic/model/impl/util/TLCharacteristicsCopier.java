/*
 * SPDX-FileCopyrightText: 2017 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.model.impl.util;

import com.top_logic.model.TLAssociationEnd;
import com.top_logic.model.TLClassPart;
import com.top_logic.model.TLReference;
import com.top_logic.model.TLStructuredTypePart;
import com.top_logic.model.util.TLModelUtil;

/**
 * Copies all characteristics from a source {@link TLClassPart} to a destination
 * {@link TLClassPart}, exception name and type.
 * 
 * @author <a href="mailto:jst@top-logic.com">Jan Stolzenburg</a>
 */
public class TLCharacteristicsCopier {

	/**
	 * Copy all characteristics from the source {@link TLClassPart} to the destination
	 * {@link TLClassPart}, exception name and type.
	 * 
	 * @param source
	 *        Is not allowed to be null.
	 * @param destination
	 *        Is not allowed to be null.
	 */
	public static void copyCharacteristics(TLClassPart source, TLClassPart destination) {
		assert destination.getName().equals(source.getName());
		copyCharacteristicsInternal(source, destination);
		assert TLModelUtil.isReference(destination) == TLModelUtil.isReference(source);
		boolean isReference = TLModelUtil.isReference(destination) && TLModelUtil.isReference(source);
		if (isReference) {
			TLAssociationEnd destinationEnd = ((TLReference) destination).getEnd();
			TLAssociationEnd sourceEnd = ((TLReference) source).getEnd();
			copyCharacteristicsInternal(sourceEnd, destinationEnd);
			destinationEnd.setAggregate(sourceEnd.isAggregate());
			destinationEnd.setComposite(sourceEnd.isComposite());
			destinationEnd.setNavigate(sourceEnd.canNavigate());
			destinationEnd.setHistoryType(sourceEnd.getHistoryType());
		}
	}

	private static void copyCharacteristicsInternal(TLStructuredTypePart source, TLStructuredTypePart destination) {
		destination.setBag(source.isBag());
		destination.setMandatory(source.isMandatory());
		destination.setMultiple(source.isMultiple());
		destination.setOrdered(source.isOrdered());
	}

}
