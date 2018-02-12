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
package com.arvatosystems.t9t.bpmn2.be.activiti;

import java.util.Collections;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.bpmn.behavior.CallActivityBehavior;
import org.activiti.engine.impl.bpmn.data.AbstractDataAssociation;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;

import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.bpmn2.be.ProcessIdGenerator;

import de.jpaw.dp.Jdp;


/**
 * Custom implementation of the BPMN 2.0 call activity (limited currently to calling a subprocess and not (yet) a global task).
 * @author WIBO001
 *
 */
public class T9tActivityBehavior extends CallActivityBehavior {

    private static final long serialVersionUID = 193475987L;

    private final CallActivityBehavior callActivityBehavior;

    public T9tActivityBehavior(CallActivityBehavior activityBehavior) {
        super(activityBehavior.getProcessDefinitonKey(), Collections.EMPTY_LIST);
        this.callActivityBehavior = activityBehavior;
    }

    @Override
    public void execute(ActivityExecution execution) throws Exception {
        RequestContext requestContext = Jdp.getRequired(RequestContext.class);
        String processDefinitionId = callActivityBehavior.getProcessDefinitonKey();
        String internalProcessDefinitionId = ProcessIdGenerator.generateId(requestContext.tenantId, processDefinitionId);
        callActivityBehavior.setProcessDefinitonKey(internalProcessDefinitionId);

        callActivityBehavior.execute(execution);
    }

    @Override
    public void addDataInputAssociation(AbstractDataAssociation dataInputAssociation) {
        callActivityBehavior.addDataInputAssociation(dataInputAssociation);
    }

    @Override
    public void addDataOutputAssociation(AbstractDataAssociation dataOutputAssociation) {
        callActivityBehavior.addDataOutputAssociation(dataOutputAssociation);
    }

    @Override
    public void setProcessDefinitonKey(String processDefinitonKey) {
        callActivityBehavior.setProcessDefinitonKey(processDefinitonKey);
    }

    @Override
    public String getProcessDefinitonKey() {
        return callActivityBehavior.getProcessDefinitonKey();
    }

    @Override
    public void completing(DelegateExecution execution,
            DelegateExecution subProcessInstance) throws Exception {
        callActivityBehavior.completing(execution, subProcessInstance);
    }

    @Override
    public void completed(ActivityExecution execution) throws Exception {
        callActivityBehavior.completed(execution);
    }
}
