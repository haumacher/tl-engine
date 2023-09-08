/*
 * SPDX-FileCopyrightText: 2001 (c) Business Operation Systems GmbH <info@top-logic.com>
 * 
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-BOS-TopLogic-1.0
 */
package com.top_logic.contact.mandatoraware.imp;

import java.io.File;

import com.top_logic.basic.config.InstantiationContext;
import com.top_logic.layout.progress.ProgressInfo;
import com.top_logic.mig.html.layout.CSVFileUploadComponent;
import com.top_logic.mig.html.layout.ComponentName;
import com.top_logic.tool.boundsec.CommandHandler;
import com.top_logic.tool.boundsec.CommandHandlerFactory;
import com.top_logic.tool.boundsec.assistent.AbstractAssistentController;
import com.top_logic.tool.boundsec.assistent.CommandHolder;
import com.top_logic.tool.boundsec.commandhandlers.UploadHandler;

/**
 * @author     <a href="mailto:mga@top-logic.com">Michael G�nsler</a>
 */
public class AbstractExcelImportController extends AbstractAssistentController {

	public AbstractExcelImportController(InstantiationContext context, Config config) {
		super(context, config);
	}

	protected ComponentName getUploadStep() {
		return this.assistent.getName().append("Upload");
    }

	protected ComponentName getImportStep() {
		return this.assistent.getName().append("Progress");
    }

    /** 
     * "excelPersonContactImportProgress" is our second and last step.
     */
    @Override
	protected boolean isFinishStep(ComponentName aCurrentStepName) {
        return this.getImportStep().equals(aCurrentStepName);
    }

    /** 
     * Same as {@link #isFinishStep(ComponentName)}.
     */
    @Override
	public boolean isLastStep(ComponentName aCurrentStepName) {
        return isFinishStep(aCurrentStepName);
    }

    /** 
     * No single close step here for now.
     * 
     * @return always false for now.
     */
    @Override
	protected boolean isCloseInfoStep(ComponentName aCurrentStepName) {
        return false;
    }

    /** 
     * My first step is named upload.
     */
    @Override
	protected boolean isFirstStep(ComponentName aCurrentStepName) {
        return this.getUploadStep().equals(aCurrentStepName);
    }

    /** 
     * Main step logic is handled here.
     */
    @Override
	public ComponentName getNameOfNextStep(ComponentName aCurrentStepName) {
        if (this.getUploadStep().equals(aCurrentStepName))
            return this.getImportStep();
        return this.getUploadStep();
    }
    
    /**
     * Add an Upload Command as part of switching to the next step.
     * 
     * @param aCurrentStepName the step performing the command.
     * @return the commandHanlder to perform before the switch action. May be null.
     */
    @Override
	public CommandHolder getAdditionaleCommandForNext(ComponentName aCurrentStepName) {
        if (this.getUploadStep().equals(aCurrentStepName)) {
            CommandHandlerFactory theFactory = CommandHandlerFactory.getInstance();
            CommandHandler theHandler = theFactory.getHandler(UploadHandler.COMMAND);
            return new CommandHolder(this.assistent.getMainLayout(), theHandler, aCurrentStepName);
        }
        return super.getAdditionaleCommandForNext(aCurrentStepName);
    }
 
    /** 
     * Check if CSVFileUploadComponent created a file an eventually delete it.
     */
    @Override
	public void dialogFinished() {
        File theFile = (File) assistent.getData(CSVFileUploadComponent.FILE_ATTR);
        if (theFile != null && theFile.exists()) {
            theFile.delete();
        }
    }
    
    /** 
     * true for upload step only.
     */
    @Override
	public boolean showCancelButton(ComponentName aCurrentStepName) {
        return this.getUploadStep().equals(aCurrentStepName);
    }
    
    /** 
     * false - not needed here.
     */
    @Override
	public boolean showBackwardButton(ComponentName aCurrentStepName) {
        return this.getImportStep().equals(aCurrentStepName);
    }

    /** 
     * DisableForwardButton when whie ProgressInfo is not finished.
     */
    @Override
	public boolean disableForwardButton(ComponentName aCurrentStepName) {
        if (this.getImportStep().equals(aCurrentStepName)) {
            ProgressInfo pInfo = (ProgressInfo) assistent.getCurrentStep().getModel();
            return pInfo != null && !pInfo.isFinished();
        }
        return super.disableForwardButton(aCurrentStepName);
    }
}
