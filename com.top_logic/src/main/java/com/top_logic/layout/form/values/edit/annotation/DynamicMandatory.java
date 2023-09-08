/*
 * SPDX-FileCopyrightText: 2017 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.form.values.edit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.top_logic.basic.config.annotation.Ref;
import com.top_logic.basic.config.annotation.TagName;
import com.top_logic.basic.func.GenericFunction;
import com.top_logic.layout.form.FormField;

/**
 * Annotation adding an algorithm to a property computing its {@link FormField#isMandatory()} state.
 * 
 * <p>
 * The annotation can be specified at the property or at its value type.
 * </p>
 * 
 * @author <a href="mailto:bhu@top-logic.com">Bernhard Haumacher</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@TagName("dynamic-mandatory")
public @interface DynamicMandatory {

	/**
	 * The {@link GenericFunction} that computes the "mandatory state" for the input field.
	 */
	Class<? extends GenericFunction<Boolean>> fun();

	/**
	 * Specification of argument references for {@link #fun() function}.
	 */
	Ref[] args() default {};

}
