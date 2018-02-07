package com.arvatosystems.t9t.bpmn.be.steps;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.bpmn.WorkflowReturnCode;

import de.jpaw.dp.Named;
import de.jpaw.dp.Singleton;

@Singleton
@Named("logger")
public class BPMStepLogger extends AbstractAlwaysRunnableNoFactoryWorkflowStep {
    private static final Logger LOGGER = LoggerFactory.getLogger(BPMStepLogger.class);

    @Override
    public WorkflowReturnCode execute(Object data, Map<String, Object> parameters) {
        Object text = parameters.get("message");
        LOGGER.info(text == null ? "(no text provided)" : text.toString());
        return WorkflowReturnCode.PROCEED_NEXT;
    }
}
