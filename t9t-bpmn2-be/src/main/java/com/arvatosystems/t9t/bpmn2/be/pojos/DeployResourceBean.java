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
package com.arvatosystems.t9t.bpmn2.be.pojos;

import java.io.InputStream;
import java.io.Serializable;

import com.arvatosystems.t9t.bpmn.pojo.ProcessDefinition;

/**
 *
 * Provide resource information for BPM deployment
 * @author WIBO001
 *
 */
public class DeployResourceBean implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 3435163960960022578L;

    private String tenantId;
    private ProcessDefinition processDefinition;
    private InputStream inputStream;
    private String deploymentName;
    private String contentResource;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getDeploymentName() {
        return deploymentName;
    }

    public void setDeploymentName(String deploymentName) {
        this.deploymentName = deploymentName;
    }

    public String getContentResource() {
        return contentResource;
    }

    public void setContentResource(String contentResource) {
        this.contentResource = contentResource;
    }

}
