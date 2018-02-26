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
package com.arvatosystems.t9t.bpmn2.be.camunda.startup.impl;

import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.core.model.CoreModelElement;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.util.xml.Element;

import com.arvatosystems.t9t.bpmn2.be.camunda.listener.MDCListener;
import com.arvatosystems.t9t.bpmn2.be.camunda.listener.WorkflowStartContextListener;

import de.jpaw.dp.Jdp;

public class T9tBPMNParseListener extends AbstractBpmnParseListener {

    private void addMDCListener(CoreModelElement modelElement) {
        final MDCListener listener = Jdp.getRequired(MDCListener.class);

        modelElement.addListener(MDCListener.EVENTNAME_START, listener);
        modelElement.addListener(MDCListener.EVENTNAME_END, listener);
    }

    @Override
    public void parseProcess(Element processElement, ProcessDefinitionEntity processDefinition) {
        final WorkflowStartContextListener listener = Jdp.getRequired(WorkflowStartContextListener.class);

        processDefinition.addListener(WorkflowStartContextListener.EVENTNAME_START, listener);
    }

    @Override
    public void parseScriptTask(Element scriptTaskElement, ScopeImpl scope, ActivityImpl activity) {
        addMDCListener(activity);
    }

    @Override
    public void parseSendTask(Element sendTaskElement, ScopeImpl scope, ActivityImpl activity) {
        addMDCListener(activity);
    }

    @Override
    public void parseBusinessRuleTask(Element businessRuleTaskElement, ScopeImpl scope, ActivityImpl activity) {
        addMDCListener(activity);
    }

    @Override
    public void parseServiceTask(Element serviceTaskElement, ScopeImpl scope, ActivityImpl activity) {
        addMDCListener(activity);
    }

}
