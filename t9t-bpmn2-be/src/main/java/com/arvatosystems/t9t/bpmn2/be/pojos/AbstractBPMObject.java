package com.arvatosystems.t9t.bpmn2.be.pojos;

import com.arvatosystems.t9t.bpmn.WorkflowReturnCode;
import com.arvatosystems.t9t.bpmn2.be.IBPMObject;

/**
 * @author LOWM005
 * @author NGTZ001 migrated for workflow V2
 */
public abstract class AbstractBPMObject implements IBPMObject {

    private WorkflowReturnCode workflowReturnCode;

    @Override
    public WorkflowReturnCode getWorkflowReturnCode() {
        return workflowReturnCode;
    }

    @Override
    public void setWorkflowReturnCode(final WorkflowReturnCode workflowReturnCode) {
        this.workflowReturnCode = workflowReturnCode;
    }
}
