/*
 * SPDX-FileCopyrightText: 2009 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.layout.unit;

import java.util.Map;

import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.event.ModelTrackingService;
import com.top_logic.knowledge.service.KnowledgeBaseFactory;
import com.top_logic.knowledge.wrap.unit.Unit;
import com.top_logic.knowledge.wrap.unit.UnitWrapper;
import com.top_logic.layout.form.FormContainer;
import com.top_logic.layout.form.component.AbstractCreateCommandHandler;
import com.top_logic.mig.html.layout.LayoutComponent;
import com.top_logic.tool.boundsec.CommandHandler;

/**
 * This {@link CommandHandler} creates a new {@link Unit} instance.
 * 
 * @author     <a href="mailto:TEH@top-logic.com">Tobias Ehrler</a>
 */
public class NewUnitCommandHandler extends AbstractCreateCommandHandler {

	/** ID of this handler. */
	public static final String	COMMAND_ID	= "newUnit";

	public NewUnitCommandHandler(InstantiationContext context, Config config) {
		super(context, config);
	}

	@Override
	public Object createObject(LayoutComponent component, Object createContext, FormContainer formContainer,
			Map<String, Object> arguments) {
		String theName         = (String) formContainer.getField(UnitWrapper.NAME_ATTRIBUTE).getValue();
		String theFormat =
			(String) formContainer.getField(UnitWrapper.FORMAT).getValue();
		Integer theSort = (Integer) formContainer.getField(UnitWrapper.SORT_ORDER).getValue();

		Unit theUnit = UnitWrapper.createUnit(KnowledgeBaseFactory.getInstance().getDefaultKnowledgeBase(), theName, theSort.intValue());
		theUnit.setFormatSpec(theFormat);
		this.sendEvent(theUnit);

		return theUnit;
	}

	/**
	 * Hook for subclasses. This method sends a MonitorEvent on the ModelTrackingService.
	 * Overwrite this method if you want to deactivate or send special MonitorEvents.
	 * Use this method if you want to generate MonitorEvents outside a GUI-Context.
	 * MonitorEvents are generated by default. 
	 * 
	 * @param aUnit the newly created person
	 */
	protected void sendEvent(Unit aUnit) {
		ModelTrackingService.sendCreateEvent(aUnit, null);
	}
}
