package com.arvatosystems.t9t.bpmn.be.steps;

import java.util.Map;

import com.arvatosystems.t9t.bpmn.WorkflowReturnCode;

import de.jpaw.dp.Named;
import de.jpaw.dp.Singleton;

@Singleton
@Named("yieldNext")
public class BPMStepYieldNext extends AbstractAlwaysRunnableNoFactoryWorkflowStep {

    @Override
    public WorkflowReturnCode execute(Object data, Map<String, Object> parameters) {
        return WorkflowReturnCode.YIELD_NEXT;
    }
}
