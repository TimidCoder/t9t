package com.arvatosystems.t9t.bpmn.be.steps;

import java.util.Map;

import com.arvatosystems.t9t.bpmn.WorkflowReturnCode;

import de.jpaw.dp.Named;
import de.jpaw.dp.Singleton;

@Singleton
@Named("restart")
public class BPMStepRestart extends AbstractAlwaysRunnableNoFactoryWorkflowStep {

    @Override
    public WorkflowReturnCode execute(Object data, Map<String, Object> parameters) {
        return WorkflowReturnCode.COMMIT_RESTART;
    }
}
