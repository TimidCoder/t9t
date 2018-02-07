package com.arvatosystems.t9t.bpmn2.be.pojos;

import java.io.Serializable;

/**
 *
 * BPM deployment information
 * @author WIBO001
 *
 */
public class DeploymentInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2010072964382787042L;

    private long preDeploymentCount;
    private long currentDeploymentCount;

    public long getPreDeploymentCount() {
        return preDeploymentCount;
    }

    public void setPreDeploymentCount(long preDeploymentCount) {
        this.preDeploymentCount = preDeploymentCount;
    }

    public long getCurrentDeploymentCount() {
        return currentDeploymentCount;
    }

    public void setCurrentDeploymentCount(long currentDeploymentCount) {
        this.currentDeploymentCount = currentDeploymentCount;
    }

}
