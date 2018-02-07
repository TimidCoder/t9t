package com.arvatosystems.t9t.bpmn.be.steps;

import java.util.Map;

import com.arvatosystems.t9t.bpmn.IWorkflowStep;
import com.arvatosystems.t9t.bpmn.WorkflowRunnableCode;

public abstract class AbstractAlwaysRunnableNoFactoryWorkflowStep implements IWorkflowStep<Object> {
    @Override
    public String getFactoryName() {
        return null;
    }

    @Override
    public WorkflowRunnableCode mayRun(Object data, Map<String, Object> parameters) {
        return WorkflowRunnableCode.RUN;
    }
}
