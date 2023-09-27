/*
 * SPDX-FileCopyrightText: 2001 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.knowledge.gui.layout.person;

import java.util.Map;

import com.top_logic.base.security.attributes.PersonAttributes;
import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.event.ModelTrackingService;
import com.top_logic.knowledge.wrap.person.Person;
import com.top_logic.knowledge.wrap.person.PersonManager;
import com.top_logic.layout.form.FormContainer;
import com.top_logic.layout.form.component.AbstractCreateCommandHandler;
import com.top_logic.layout.form.model.FormContext;
import com.top_logic.layout.form.model.SelectField;
import com.top_logic.mig.html.layout.LayoutComponent;
import com.top_logic.util.TLContext;
import com.top_logic.util.error.TopLogicException;
import com.top_logic.util.license.LicenseTool;

/**
 * Default CommandHandler for creating a new Person in TopLogic.
 * 
 * @author <a href="mailto:mga@top-logic.com">Michael G&auml;nsler </a>
 */
@Deprecated
public class NewPersonCommandHandler extends AbstractCreateCommandHandler {

	public static final String	COMMAND_ID	= "newPerson";

    public NewPersonCommandHandler(InstantiationContext context, Config config) {
        super(context, config);
    }

	@Override
	protected void validateAdditional(LayoutComponent component, FormContext formContext, Object model) {
		Boolean isRestricted = (Boolean) formContext.getField(PersonAttributes.RESTRICTED_USER).getValue();
		if (!LicenseTool.moreUsersAllowed(LicenseTool.getInstance().getLicense(), isRestricted)) {
			throw new TopLogicException(I18NConstants.ERROR_MAXIMUM_USERS_REACHED);
		}
	}

	@Override
	public Object createObject(LayoutComponent component, Object createContext, FormContainer formContainer,
			Map<String, Object> arguments) {
        String theID           = (String) formContainer.getField(PersonAttributes.USER_NAME) .getValue();
        String theFirst        = (String) formContainer.getField(PersonAttributes.GIVEN_NAME).getValue();
        String theSur          = (String) formContainer.getField(PersonAttributes.SUR_NAME)  .getValue();
        String theTitle        = (String) formContainer.getField(PersonAttributes.TITLE)     .getValue();
		Boolean isRestricted = (Boolean) formContainer.getField(PersonAttributes.RESTRICTED_USER).getValue();
        String theDataDeviceID = (String) ((SelectField) formContainer.getField(PersonAttributes.DATA_ACCESS_DEVICE_ID)).getSingleSelection();

		Person thePerson = this.createPerson(theID, theFirst, theSur, theTitle, theDataDeviceID, null, isRestricted);
        this.sendEvent(thePerson);
        
        return thePerson;
	}

	public Person createPerson(String anID, String aFirst, String aSurname, String aTitle, String aDataDeviceID,
			String aAuthDeviceID, Boolean isRestricted) {
		Person thePerson = PersonManager.getManager().createPerson(anID, aDataDeviceID, aAuthDeviceID, isRestricted);
		if (thePerson == null) {
			throw new TopLogicException(I18NConstants.REFRESH_ACCOUNTS_FAILED);
        }
		thePerson.setValue(PersonAttributes.RESTRICTED_USER, isRestricted);
        return thePerson;
    }

    /**
     * Hook for subclasses. This method sends a MonitorEvent on the ModelTrackingService.
     * Overwrite this method if you want to deactivate or send special MonitorEvents.
     * Use this method if you want to generate MonitorEvents outside a GUI-Context.
     * MonitorEvents are generated by default. 
     * 
     * @param aPerson the newly created person
     */
    protected void sendEvent(Person aPerson) {
       ModelTrackingService.sendCreateEvent(aPerson, TLContext.getContext().getCurrentPersonWrapper());
    }
}