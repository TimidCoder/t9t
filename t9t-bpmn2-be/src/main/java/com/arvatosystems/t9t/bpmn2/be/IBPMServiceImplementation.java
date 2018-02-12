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
package com.arvatosystems.t9t.bpmn2.be;

import java.io.InputStream;
import java.util.Map;

import com.arvatosystems.t9t.bpmn.IWorkflowStep;
import com.arvatosystems.t9t.bpmn2.be.pojos.DeployResourceBean;

/**
 * This is the interface to specific implementations, like Activity or Camunda.
 *
 * @author WIBO001
 */
public interface IBPMServiceImplementation {

    /**
     * Initialize BPM engine with specific business process beans.
     *
     * @param businessProcessBeans
     *            business process beans
     */
    void init(Map<String, IWorkflowStep> businessProcessBeans);

    /**
     * Handle deployment process internally.
     *
     * @param deployInternalBean
     *            is resource information that required for deployment
     */
    void deployProcessInternal(DeployResourceBean deployInternalBean);

    /**
     * Handle the finding deployment by process definition id
     *
     * @param processDefinitionId
     *            is a {@link String} of process definition id
     * @return {@linkplain String} of deployment id
     */
    String findDeploymentByProcessDefinitionId(String processDefinitionId);

    /**
     * Get process definition diagram.
     *
     * @param deploymentId
     *            deployment id
     * @return input stream to diagram
     */
    InputStream getProcessDefinitionDiagram(String deploymentId);

    /**
     * Get process definition content.
     *
     * @param deploymentId
     *            deployment id
     * @return input stream to content
     */
    InputStream getProcessDefinitionContent(String deploymentId);

    /**
     * Start the process instance by key.
     *
     * @param internalProcessId
     *            is a {@link String} of internal process id
     * @param processParams
     *            is a {@link Map} of process parameters such as parameter
     *            output
     */
    void startProcessInstanceByKey(String internalProcessId, Map<String, Object> processParams);
}
