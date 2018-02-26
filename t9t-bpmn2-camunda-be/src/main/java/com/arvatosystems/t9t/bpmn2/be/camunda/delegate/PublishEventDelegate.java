package com.arvatosystems.t9t.bpmn2.be.camunda.delegate;

import static com.arvatosystems.t9t.bpmn2.be.camunda.utils.ExpressionUtils.getValueAsBonaPortable;
import static java.util.Objects.requireNonNull;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.arvatosystems.t9t.base.event.EventParameters;
import com.arvatosystems.t9t.base.services.IExecutor;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Singleton;

/**
 * Java delegate to publish a T9T event.
 *
 * @author TWEL006
 */
@Singleton
public class PublishEventDelegate implements JavaDelegate {

    private final IExecutor executor = Jdp.getRequired(IExecutor.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        final EventParameters eventParameters = requireNonNull(getValueAsBonaPortable(execution, "eventJson", null), "Variable 'eventJson' must not be empty");

        executor.publishEvent(eventParameters);
    }

}
