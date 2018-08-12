/*
 * Copyright (c) 2012 - 2018 Arvato Systems GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arvatosystems.t9t.bpmn.jpa.engine.impl

import com.arvatosystems.t9t.base.MessagingUtil
import com.arvatosystems.t9t.base.T9tConstants
import com.arvatosystems.t9t.base.T9tException
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.bpmn.IBPMObjectFactory
import com.arvatosystems.t9t.bpmn.IWorkflowStep
import com.arvatosystems.t9t.bpmn.ProcessDefinitionDTO
import com.arvatosystems.t9t.bpmn.ProcessExecutionStatusDTO
import com.arvatosystems.t9t.bpmn.T9tAbstractWorkflowStep
import com.arvatosystems.t9t.bpmn.T9tBPMException
import com.arvatosystems.t9t.bpmn.T9tWorkflowStepAddParameters
import com.arvatosystems.t9t.bpmn.T9tWorkflowStepJavaTask
import com.arvatosystems.t9t.bpmn.T9tWorkflowStepYield
import com.arvatosystems.t9t.bpmn.WorkflowReturnCode
import com.arvatosystems.t9t.bpmn.jpa.entities.ProcessExecStatusEntity
import com.arvatosystems.t9t.bpmn.jpa.persistence.IProcessExecStatusEntityResolver
import com.arvatosystems.t9t.bpmn.services.IBpmnEngineRunner
import com.arvatosystems.t9t.bpmn.services.IBpmnRunner
import com.arvatosystems.t9t.bpmn.services.IProcessDefinitionCache
import com.arvatosystems.t9t.bpmn.services.IWorkflowStepCache
import de.jpaw.annotations.AddLogger
import de.jpaw.dp.Inject
import de.jpaw.dp.Jdp
import de.jpaw.dp.Singleton
import java.util.HashMap
import java.util.Map
import org.joda.time.Instant
import org.slf4j.MDC
import java.util.Objects

@Singleton
@AddLogger
class BpmnRunner implements IBpmnRunner {
    @Inject IProcessExecStatusEntityResolver statusResolver
    @Inject IWorkflowStepCache workflowStepCache
    @Inject IProcessDefinitionCache pdCache

    override run(RequestContext ctx, Long statusRef) {
        //////////////////////////////////////////////////
        // prepare...
        //////////////////////////////////////////////////
        // 1.) get status entity
        val statusEntity = statusResolver.find(statusRef) //, LockModeType.PESSIMISTIC_READ)
        if (statusEntity === null) {
            LOGGER.info("Process status entry {} has disappeared... which implies end of the workflow process")
            return false
        }
        // 2.) get process configuration
        val pd = pdCache.getCachedProcessDefinitionDTO(ctx.tenantId, statusEntity.processDefinitionId)
        ctx.statusText = ctx.tenantId + ":" + pd.processDefinitionId + "(" + statusRef.toString + ")"

        // 3.) obtain a factory to initialize the object (or use a dummy)
        val factory = if (pd.factoryName === null)
                UnspecifiedFactory.FACTORY
            else
                workflowStepCache.getBPMObjectFactoryForName(pd.factoryName)

        // decide if the execution must set a lock
        val refToLock = if (pd.useExclusiveLock) factory.getRefForLock(statusEntity.targetObjectRef);
        if (refToLock !== null) {
            ctx.lockRef(refToLock, pd.jvmLockTimeoutInMillis ?: T9tConstants.DEFAULT_JVM_LOCK_TIMEOUT)
        }
        if (pd.engine !== null) {
            // use some BPMN 2 engine
            val engineRunner = Jdp.getRequired(IBpmnEngineRunner, pd.engine)
            return engineRunner.run(ctx, statusRef, pd, factory, refToLock, refToLock !== null)
        }
        // execute with the default engine
        return run(ctx, statusEntity, pd, factory, refToLock, refToLock !== null)
    }

    def protected boolean run(RequestContext ctx, ProcessExecStatusEntity statusEntity, ProcessDefinitionDTO pd, IBPMObjectFactory<?> factory, Long lockObjectRef, boolean jvmLockAcquired) {
        MDC.put(T9tConstants.MDC_BPMN_PROCESS, pd.name?:Objects.toString(pd.objectRef));
        MDC.put(T9tConstants.MDC_BPMN_PROCESS_INSTANCE, Objects.toString(statusEntity.objectRef));

        try {
            val workflowObject = factory.read(statusEntity.targetObjectRef, lockObjectRef, jvmLockAcquired)
            // only now the lock has been obtained
            statusResolver.entityManager.refresh(statusEntity)  // inside the lock, read again before we make any changes
            val parameters = statusEntity.currentParameters ?: new HashMap<String, Object>()  // do not work with the entity data, every getter / setter will convert!
            statusEntity.yieldUntil = ctx.executionStart;  // default entry for next execution

            //////////////////////////////////////////////////
            // find where to (re)start...
            //////////////////////////////////////////////////
            var int nextStepToExecute = pd.findStep(statusEntity.nextStep)
            LOGGER.debug("(Re)starting workflow {}: {} for ref {} at step {} ({})",
                ctx.tenantId, pd.processDefinitionId, statusEntity.targetObjectRef,
                nextStepToExecute, statusEntity.nextStep ?: ""
            )

            var boolean running       = true
            statusEntity.returnCode   = null    // reset issue marker
            statusEntity.errorDetails = null

            while (running) {
                // execute a step (or skip it)
                val nextStep = pd.workflow.steps.get(nextStepToExecute)
                MDC.put(T9tConstants.MDC_BPMN_STEP, nextStep.label);

                try {
                    LOGGER.debug("Starting workflow step {}: {} for ref {} at step {} ({})",
                        ctx.tenantId, pd.processDefinitionId, statusEntity.targetObjectRef,
                        nextStepToExecute, statusEntity.nextStep ?: ""
                    )
                    val WorkflowReturnCode wfReturnCode = nextStep.execute(ctx, pd, statusEntity, workflowObject, parameters)
                    LOGGER.debug("{}.{} ({}) returned {} on object {}", //, parameters are {}",
                        pd.processDefinitionId, nextStep.label, nextStep.ret$PQON, wfReturnCode, statusEntity.targetObjectRef
                    )

                    // evaluate workflow return code
                    if (wfReturnCode === WorkflowReturnCode.COMMIT_RESTART || wfReturnCode === WorkflowReturnCode.PROCEED_NEXT || wfReturnCode === WorkflowReturnCode.YIELD_NEXT) {
                        nextStepToExecute += 1
                        if (nextStepToExecute >= pd.workflow.steps.size) {
                            // implicit end
                            // remove the status entity
                            LOGGER.info("Workflow {} COMPLETED by running past end for ref {} (original return code was {})", pd.processDefinitionId, statusEntity.targetObjectRef, wfReturnCode)
                            statusResolver.entityManager.remove(statusEntity)
                            return false
                        }
                        statusEntity.nextStep = pd.workflow.steps.get(nextStepToExecute).label
                    }
                    switch (wfReturnCode) {
                        case DONE: {
                            // remove the status entity
                            LOGGER.info("Workflow {} COMPLETED with DONE for ref {}", pd.processDefinitionId, statusEntity.targetObjectRef)
                            statusResolver.entityManager.remove(statusEntity)
                            return false
                        }
                        case YIELD: {
                            // write back parameters and return, next time we restart the same step!
                            statusEntity.currentParameters = if (parameters.empty) null else parameters
                            return false
                        }
                        case COMMIT_RESTART: {
                            // common code executed before...
                            return true
                        }
                        case PROCEED_NEXT: {
                            // common code executed before...
                            // fall through (keep running)
                        }
                        case YIELD_NEXT: {
                            // common code executed before...
                            // write back parameters and return, next time we restart the next step!
                            statusEntity.currentParameters = if (parameters.empty) null else parameters
                            return false
                        }
                        case ERROR: {
                            // nothing to do?
                        }
                    }
                } finally {
                    MDC.remove(T9tConstants.MDC_BPMN_STEP);
                }
            }
            return false
        } finally {
            MDC.remove(T9tConstants.MDC_BPMN_PROCESS);
            MDC.remove(T9tConstants.MDC_BPMN_PROCESS_INSTANCE);
        }
    }

    def int findStep(ProcessDefinitionDTO pd, String label) {
        if (label === null || pd.alwaysRestartAtStep1)
            return 0
        val steps = pd.workflow.steps
        for (var int i = 0; i < steps.size; i += 1)
            if (steps.get(i).label == label)
                return i
        throw new T9tBPMException(T9tBPMException.BPM_LABEL_NOT_FOUND, pd.processDefinitionId + ": " + label)
    }

    def protected WorkflowReturnCode dealWithError(ProcessExecStatusEntity statusEntity, Map<String, Object> parameters) {
        val retCode = parameters.get("returnCode")
        statusEntity.returnCode = if (retCode === null || !(retCode instanceof Integer))
            T9tBPMException.BPM_NO_ERROR
        else
            retCode as Integer;
        statusEntity.errorDetails = MessagingUtil.truncField(parameters.get("errorDetails"), ProcessExecutionStatusDTO.meta$$errorDetails.length)
        parameters.remove("returnCode")
        parameters.remove("errorDetails")
        return WorkflowReturnCode.YIELD
    }

    def protected WorkflowReturnCode dealWithDelay(ProcessExecStatusEntity statusEntity, Map<String, Object> parameters, WorkflowReturnCode code) {
        val tilWhen = parameters.get("yieldUntil")
        if (tilWhen !== null && tilWhen instanceof Instant) {
            statusEntity.yieldUntil = tilWhen as Instant
        }
        return code;
    }

    def dispatch WorkflowReturnCode execute(T9tWorkflowStepJavaTask step,
        RequestContext ctx, ProcessDefinitionDTO pd, ProcessExecStatusEntity statusEntity,
        Object workflowObject, Map<String, Object> parameters
    ) {
        val javaWfStep = workflowStepCache.getWorkflowStepForName(step.stepName) as IWorkflowStep<Object>
        val runnable = javaWfStep.mayRun(workflowObject, parameters)
        if (runnable === null) {
            // coding issue
            LOGGER.error("step {}.{}.mayRun returned code null", pd.processDefinitionId, step.stepName)
            statusEntity.returnCode = T9tBPMException.BPM_EXECUTE_JAVA_TASK_RETURNED_NULL
            statusEntity.errorDetails = step.stepName
            return WorkflowReturnCode.YIELD
        }
        LOGGER.trace("Executing java task {} in workflow {} (mayRun returned {})", step.stepName, pd.processDefinitionId, runnable)
        switch (runnable) {
            case RUN: {
                val execCode = javaWfStep.execute(workflowObject, parameters)
                if (execCode === null) {
                    // coding issue
                    LOGGER.error("step {}.{}.execute returned code null", pd.processDefinitionId, step.stepName)
                    statusEntity.returnCode = T9tBPMException.BPM_EXECUTE_JAVA_TASK_RETURNED_NULL
                    statusEntity.errorDetails = step.stepName
                    return WorkflowReturnCode.YIELD
                }
                switch (execCode) {
                case ERROR:
                    return dealWithError(statusEntity, parameters)
                case YIELD:
                    return dealWithDelay(statusEntity, parameters, execCode)
                case YIELD_NEXT:
                    return dealWithDelay(statusEntity, parameters, execCode)
                default:
                    return execCode
                }
            }
            case SKIP: {
                return WorkflowReturnCode.PROCEED_NEXT
            }
            case ERROR: {
                return dealWithError(statusEntity, parameters)
            }
            case YIELD: {
                return WorkflowReturnCode.YIELD
            }
        }

        return WorkflowReturnCode.PROCEED_NEXT
    }

    def dispatch WorkflowReturnCode execute(T9tWorkflowStepAddParameters step,
        RequestContext ctx, ProcessDefinitionDTO pd, ProcessExecStatusEntity statusEntity,
        Object workflowObject, Map<String, Object> parameters
    ) {
        for (e : step.parameters.entrySet)
            parameters.put(e.key, e.value)
        return WorkflowReturnCode.PROCEED_NEXT
    }

    def dispatch WorkflowReturnCode execute(T9tWorkflowStepYield step,
        RequestContext ctx, ProcessDefinitionDTO pd, ProcessExecStatusEntity statusEntity,
        Object workflowObject, Map<String, Object> parameters
    ) {
        statusEntity.yieldUntil = ctx.executionStart.plus(1000L * step.waitSeconds)
        return WorkflowReturnCode.YIELD
    }

    def dispatch WorkflowReturnCode execute(T9tAbstractWorkflowStep step,
        RequestContext ctx, ProcessDefinitionDTO pd, ProcessExecStatusEntity statusEntity,
        Object workflowObject, Map<String, Object> parameters
    ) {
        throw new T9tException(T9tException.NOT_YET_IMPLEMENTED, "Workflow step type " + step.class.canonicalName)
    }
}
