package com.arvatosystems.t9t.bpmn2.be;

import java.io.Serializable;

import com.arvatosystems.t9t.bpmn.WorkflowReturnCode;

// migrated for workflow V2
 public interface IBPMObject extends Serializable {
    /**
     * Returns the target object reference.
     * @return target object reference
     */
    Long getObjectRef();

    /**
     * Returns the workflow return code.
     * @return workflow return code
     */
    WorkflowReturnCode getWorkflowReturnCode();

    /**
     * Set a new workflow return code.
     * @param workflowReturnCode given new workflow return code
     */
    void setWorkflowReturnCode(WorkflowReturnCode workflowReturnCode);
}
