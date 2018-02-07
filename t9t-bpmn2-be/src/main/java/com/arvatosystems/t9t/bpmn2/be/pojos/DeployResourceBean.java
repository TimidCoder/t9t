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
