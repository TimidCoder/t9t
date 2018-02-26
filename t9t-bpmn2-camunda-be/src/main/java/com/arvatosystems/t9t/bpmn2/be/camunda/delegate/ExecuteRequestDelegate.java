package com.arvatosystems.t9t.bpmn2.be.camunda.delegate;

import static com.arvatosystems.t9t.bpmn2.be.camunda.utils.ExpressionUtils.getValueAsBonaPortable;
import static java.util.Objects.requireNonNull;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.services.IExecutor;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Singleton;

/**
 * Java delegate for execution of arbitrary requests. If a responseVariable name is provided, the response is also
 * provided. Error return codes of the executed request are provided using a BPMN Error.
 *
 * @author TWEL006
 */
@Singleton
public class ExecuteRequestDelegate implements JavaDelegate {

    private final IExecutor executor = Jdp.getRequired(IExecutor.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        final RequestParameters requestObject = requireNonNull(getValueAsBonaPortable(execution, "requestJson", null), "Variable 'requestJson' must not be empty");

        final ServiceResponse response = executor.executeSynchronous(requestObject);
        execution.setVariableLocal("response", response);

        if (response.getReturnCode() != 0) {
            throw new BpmnError(Integer.toString(response.getReturnCode()), response.getErrorMessage());
        }
    }

}
