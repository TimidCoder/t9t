/*
 * Copyright (c) 2012 - 2018 Arvato Systems GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.arvatosystems.t9t.bpmn2.be.camunda.delegate;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.bpmn.IWorkflowStep;
import com.arvatosystems.t9t.bpmn.WorkflowReturnCode;
import com.arvatosystems.t9t.bpmn.WorkflowRunnableCode;
import com.arvatosystems.t9t.bpmn2.IBPMNObjectFactory;
import com.arvatosystems.t9t.bpmn2.T9tBPMNConstants;
import com.arvatosystems.t9t.bpmn2.be.camunda.utils.WorkflowStepParameterMapAdapter;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Singleton;
import de.jpaw.util.ApplicationException;

/**
 * <p>
 * Java delegate to execute a {@link IWorkflowStep}. The workflow return code of the executed step can be provided using
 * a given variable name.
 * </p>
 *
 * <p>
 * Application exceptions during execution are provided as an BPMN error using the given error code. The workflow return
 * code {@link WorkflowReturnCode#ERROR} also results in an BPMN error with error code
 * {@link T9tBPMNConstants#ERROR_CODE_STEP_ERROR}.
 * </p>
 *
 * <p>
 * Please note: The workflow return codes {@link WorkflowReturnCode#YIELD}, {@link WorkflowReturnCode#YIELD_NEXT},
 * {@link WorkflowReturnCode#COMMIT_RESTART} does not trigger and interrupt or commit and restarts in BPMN handling.
 * Furthermore, the return code {@link WorkflowReturnCode#DONE} does not stop the BPMN execution. If desired, the
 * workflow return code must be mapped to a variable and such handling must be provided direktly in BPMN.
 * </p>
 *
 * @author TWEL006
 */
@Singleton
public class WorkflowStepDelegate implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowStepDelegate.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        final String nameString = requireNonNull(Objects.toString(execution.getVariable("workflowStepName"), null),
                                                 "Variable 'workflowStepName' must not be empty");

        LOGGER.debug("Start execute workflow step '{}'", nameString);
        final IWorkflowStep<Object> workflowStep = Jdp.getRequired(IWorkflowStep.class, nameString);

        IBPMNObjectFactory<Object> objectFactory;
        if (workflowStep.getFactoryName() != null) {
            objectFactory = Jdp.getRequired(IBPMNObjectFactory.class, workflowStep.getFactoryName());
        } else {
            objectFactory = Jdp.getRequired(IBPMNObjectFactory.class);
        }

        final WorkflowStepParameterMapAdapter context = new WorkflowStepParameterMapAdapter(execution);
        final Object data = objectFactory.create(context);

        WorkflowReturnCode returnCode = WorkflowReturnCode.PROCEED_NEXT;
        String errorCode = null;
        String errorMessage = null;
        try {
            // Check what to do
            WorkflowRunnableCode runnableCode = workflowStep.mayRun(data, context);
            if (runnableCode == null) {
                runnableCode = WorkflowRunnableCode.RUN;
            }

            switch (runnableCode) {
            case ERROR: {
                returnCode = WorkflowReturnCode.ERROR;
                break;
            }

            case SKIP: {
                returnCode = WorkflowReturnCode.PROCEED_NEXT;
                break;
            }

            case YIELD: {
                returnCode = WorkflowReturnCode.YIELD;
                break;
            }

            case RUN: {
                returnCode = workflowStep.execute(data, context);
                break;
            }
            }
        } catch (ApplicationException e) {
            returnCode = WorkflowReturnCode.ERROR;
            errorCode = Integer.toString(e.getErrorCode());
            errorMessage = e.getMessage();
        }

        execution.setVariableLocal("resultValue", returnCode);

        LOGGER.debug("End execute workflow step '{}' - return code is {}", nameString, returnCode);

        if (returnCode == WorkflowReturnCode.ERROR) {
            if (errorCode == null) {
                errorCode = T9tBPMNConstants.ERROR_CODE_STEP_ERROR;
            }

            throw new BpmnError(errorCode, errorMessage);
        }
    }

}
