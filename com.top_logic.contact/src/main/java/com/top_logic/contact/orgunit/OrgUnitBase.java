/*
 * SPDX-FileCopyrightText: 2011 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.contact.orgunit;

/**
 * Interface for {@link OrgUnitBaseConstants#ME_ORG_UNIT_BASE} business objects.
 * 
 * @author Automatically generated by <code>com.top_logic.element.binding.gen.InterfaceGenerator</code>
 */
public interface OrgUnitBase extends OrgUnitAll, OrgUnitBaseConstants {

	/** Getter for attribute {@link #ATTRIBUTE_ORG_ID}. */
	String getOrgID();

	/** Setter for attribute {@link #ATTRIBUTE_ORG_ID}. */
	void setOrgID(String newValue);

	/** Getter for attribute {@link #ATTRIBUTE_BOSS}. */
	Object getBoss();

	/** Setter for attribute {@link #ATTRIBUTE_BOSS}. */
	void setBoss(Object newValue);

	/** Getter for attribute {@link #ATTRIBUTE_MEMBER}. */
	java.util.Collection getMember();

	/** Setter for attribute {@link #ATTRIBUTE_MEMBER}. */
	void setMember(java.util.Collection newValue);

}
