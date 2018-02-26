package com.arvatosystems.t9t.bpmn2.be.camunda.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.MDC;

import de.jpaw.dp.Singleton;

@Singleton
public class MDCListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) throws Exception {

        switch (execution.getEventName()) {

        case EVENTNAME_START: {
            MDC.put("BPMN2Activiti", execution.getCurrentActivityId());
            MDC.put("BPMN2BusinessKey", execution.getProcessBusinessKey());
            MDC.put("BPMN2ProcessDefinitionId", execution.getProcessDefinitionId());
            break;
        }

        case EVENTNAME_END: {
            MDC.remove("BPMN2Activiti");
            MDC.remove("BPMN2BusinessKey");
            MDC.remove("BPMN2ProcessDefinitionId");
            break;
        }
        }

    }

}
