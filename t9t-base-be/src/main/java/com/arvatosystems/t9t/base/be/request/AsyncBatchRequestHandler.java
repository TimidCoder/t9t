package com.arvatosystems.t9t.base.be.request;

import com.arvatosystems.t9t.base.T9tConstants;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.request.AsyncBatchRequest;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.IExecutor;

import de.jpaw.dp.Jdp;

public class AsyncBatchRequestHandler extends AbstractRequestHandler<AsyncBatchRequest>  {

    // @Inject
    protected final IExecutor messaging = Jdp.getRequired(IExecutor.class);

    @Override
    public ServiceResponse execute(AsyncBatchRequest request) {
        ServiceResponse resp = messaging.executeSynchronous(request.getPrimaryRequest());
        if (resp.getReturnCode() <= (request.getAllowNo() ? T9tConstants.MAX_DECLINE_RETURN_CODE : T9tConstants.MAX_OK_RETURN_CODE)) {
            // fine (preliminary check), secondary will be done after commit, to capture late exceptions
            messaging.executeAsynchronous(request.getAsyncRequest());
        }
        return resp;
    }
}
