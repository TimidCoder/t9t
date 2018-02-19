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
package com.arvatosystems.t9t.bpmn2.be.camunda.listener;

import static com.arvatosystems.t9t.bpmn2.be.camunda.BPMN2Constants.PROPERTY_WORKFLOW_TYPE;
import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Collectors.toMap;

import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.bpmn2.IBPMNInitWorkflowCallback;
import com.arvatosystems.t9t.bpmn2.be.camunda.utils.WorkflowStepParameterMapAdapter;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Singleton;

@Singleton
public class WorkflowStartContextListener implements ExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowStartContextListener.class);

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        if (!EVENTNAME_START.equals(execution.getEventName())) {
            return;
        }

        final ExtensionElements extensionElements = execution.getBpmnModelElementInstance()
                                                             .getExtensionElements();
        final Map<String, String> extensionProperties;
        if (extensionElements != null) {
            extensionProperties = unmodifiableMap(extensionElements.getElementsQuery()
                                                                   .filterByType(CamundaProperties.class)
                                                                   .list()
                                                                   .stream()
                                                                   .flatMap(properties -> properties.getCamundaProperties()
                                                                                                    .stream())
                                                                   .collect(toMap(CamundaProperty::getCamundaName,
                                                                           CamundaProperty::getCamundaValue)));
        } else {
            extensionProperties = emptyMap();
        }

        final String workflowTypeString = extensionProperties.get(PROPERTY_WORKFLOW_TYPE);

        if (workflowTypeString != null) {
            LOGGER.debug("Init workflow with type '{}'", workflowTypeString);
            final IBPMNInitWorkflowCallback initCallback = Jdp.getRequired(IBPMNInitWorkflowCallback.class, workflowTypeString);
            initCallback.init(new WorkflowStepParameterMapAdapter(execution), extensionProperties);
        }

    }

}
