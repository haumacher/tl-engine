/*
 * SPDX-FileCopyrightText: 2003 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.form.model;

import java.util.Collection;

import com.top_logic.basic.col.TypedAnnotatable;
import com.top_logic.layout.ResPrefix;
import com.top_logic.layout.ResourceView;
import com.top_logic.layout.form.FormHandler;
import com.top_logic.layout.form.FormMember;
import com.top_logic.layout.form.FormMemberVisitor;
import com.top_logic.layout.form.component.FormComponent;
import com.top_logic.mig.html.layout.LayoutComponent;

/**
 * Top-level {@link FormGroup} in a {@link FormComponent}.
 *
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
public class FormContext extends FormGroup {

	private static final Property<FormHandler> PROPERTY_CONTEXT_HANDLER =
		TypedAnnotatable.property(FormHandler.class, "contextHandler");

	/**
	 * Constant for the convenience constructor
	 * {@link #FormContext(String, ResPrefix, FormMember[])}.
	 */
    public static final FormMember[] NO_FORM_MEMBERS = new FormMember[] {};

    /**
	 * Create a FormContext from an array of predefined members.
	 * 
	 * @param resPrefix
	 *        Prefix to translate error String and labels.
	 */
	public FormContext(String name, ResPrefix resPrefix, FormMember[] members) {
		super(name, resPrefix, members);
	}

    /**
	 * Create a FormContext from an array of predefined members.
	 * 
	 * @param aI18nPrefix
	 *     Prefix to translate error String and labels.
	 */
	public FormContext(String name, ResPrefix aI18nPrefix, Collection members) {
		super(name, aI18nPrefix, members);
	}

    /**
     * Create a FormContext for the given layout component without any members.
     */
    public FormContext(LayoutComponent aComponent) {
		this(aComponent.getName().localName() + "_FormContext", aComponent.getResPrefix(), NO_FORM_MEMBERS);
    }

    public FormContext(String name, ResourceView resourceView) {
		super(name, resourceView);
	}
    
    /**
     * Create a FormContext without any members.
     * 
     * Is useful for graceful exception handling and such.
     * 
     * @param aI18nPrefix
     *            Prefix to translate error String and labels.
     */
	public FormContext(String name, ResPrefix aI18nPrefix) {
        this(name, aI18nPrefix, NO_FORM_MEMBERS);
    }

	/**
     * Return this unless this has a parent, too.
     */
	@Override
	public FormContext getFormContext() {
		if (getParent() == null) {
			return this;
		} else {
			return super.getFormContext();
		}
	}

	@Override
	public Object visit(FormMemberVisitor v, Object arg) {
		return v.visitFormContext(this, arg);
	}
	/**
	 * Returns the {@link FormHandler} containing this {@link FormContext}.
	 */
	public FormHandler getOwningModel() {
		return get(PROPERTY_CONTEXT_HANDLER);
	}

	/**
	 * Stores {@link FormHandler} containing this {@link FormContext} for later access via
	 * {@link FormContext#getOwningModel()}.
	 */
	public FormHandler setOwningModel(FormHandler model) {
		return set(PROPERTY_CONTEXT_HANDLER, model);
	}

}
