package com.arvatosystems.t9t.bpmn2.be.camunda.delegate;

import static com.arvatosystems.t9t.bpmn2.be.camunda.utils.ExpressionUtils.getValueAsBonaPortable;
import static com.arvatosystems.t9t.bpmn2.be.camunda.utils.ExpressionUtils.getValueAsString;
import static java.util.Objects.requireNonNull;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.services.IExecutor;

import de.jpaw.dp.Jdp;

/**
 * Java delegate for execution of arbitrary requests. If a responseVariable name is provided, the response is also
 * provided. Error return codes of the executed request are provided using a BPMN Error.
 *
 * @author TWEL006
 */
public class ExecuteRequestDelegate implements JavaDelegate {

    private final IExecutor executor = Jdp.getRequired(IExecutor.class);

    private Expression requestJson;
    private Expression responseVariable;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        final RequestParameters requestObject = requireNonNull(getValueAsBonaPortable(requestJson, execution, null), "Parameter 'request' must not be empty");
        final String responseVariableName = getValueAsString(responseVariable, execution, null);

        final ServiceResponse response = executor.executeSynchronous(requestObject);

        if (responseVariableName != null) {
            execution.setVariable(responseVariableName, response);
        }

        if (response.getReturnCode() != 0) {
            throw new BpmnError(Integer.toString(response.getReturnCode()), response.getErrorMessage());
        }
    }

    public Expression getResponseVariable() {
        return responseVariable;
    }

    public void setResponseVariable(Expression responseVariable) {
        this.responseVariable = responseVariable;
    }

    public Expression getRequestJson() {
        return requestJson;
    }

    public void setRequestJson(Expression requestJson) {
        this.requestJson = requestJson;
    }

}
