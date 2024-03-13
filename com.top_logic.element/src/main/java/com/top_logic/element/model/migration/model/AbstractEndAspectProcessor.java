/*
 * SPDX-FileCopyrightText: 2021 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.element.model.migration.model;

import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.basic.config.annotation.Label;
import com.top_logic.basic.config.annotation.Name;
import com.top_logic.dob.meta.MOReference.HistoryType;
import com.top_logic.element.config.EndAspect;
import com.top_logic.knowledge.service.migration.MigrationProcessor;

/**
 * {@link MigrationProcessor} handling properties like an {@link EndAspect}.
 * 
 * @author <a href="mailto:daniel.busche@top-logic.com">Daniel Busche</a>
 */
public abstract class AbstractEndAspectProcessor<C extends AbstractEndAspectProcessor.Config<?>>
		extends AbstractCreateTypePartProcessor<C> {

	/**
	 * Typed configuration interface definition for {@link AbstractEndAspectProcessor}.
	 * 
	 * @author <a href="mailto:dbu@top-logic.com">dbu</a>
	 */
	public interface Config<I extends AbstractEndAspectProcessor<?>> extends AbstractCreateTypePartProcessor.Config<I> {

		/**
		 * See {@link EndAspect#isComposite()}.
		 */
		@Name(EndAspect.COMPOSITE_PROPERTY)
		boolean isComposite();

		/**
		 * Setter for {@link #isComposite()}.
		 */
		void setComposite(boolean value);

		/**
		 * See {@link EndAspect#isAggregate()}.
		 */
		@Name(EndAspect.AGGREGATE_PROPERTY)
		boolean isAggregate();

		/**
		 * Setter for {@link #isAggregate()}.
		 */
		void setAggregate(boolean value);

		/**
		 * See {@link EndAspect#canNavigate()}.
		 */
		@Name(EndAspect.NAVIGATE_PROPERTY)
		boolean canNavigate();

		/**
		 * Setter for {@link #canNavigate()}.
		 */
		void setNavigate(boolean value);

		/**
		 * See {@link EndAspect#getHistoryType()}.
		 */
		@Name(EndAspect.HISTORY_TYPE_PROPERTY)
		@Label("Historization")
		HistoryType getHistoryType();

		/**
		 * Setter for {@link #getHistoryType()}.
		 */
		void setHistoryType(HistoryType value);
	}

	/**
	 * Create a {@link AbstractEndAspectProcessor}.
	 * 
	 * @param context
	 *            the {@link InstantiationContext} to create the new object in
	 * @param config
	 *            the configuration object to be used for instantiation
	 */
	public AbstractEndAspectProcessor(InstantiationContext context, C config) {
		super(context, config);
	}

}

