package com.arvatosystems.t9t.base.be.request;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.request.ExecuteAsyncRequest;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.IExecutor;
import com.arvatosystems.t9t.base.services.RequestContext;

import de.jpaw.dp.Jdp;

public class ExecuteAsyncRequestHandler extends AbstractRequestHandler<ExecuteAsyncRequest> {

    protected final IExecutor executor = Jdp.getRequired(IExecutor.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, ExecuteAsyncRequest request) throws Exception {
        executor.executeAsynchronous(ctx, request.getAsyncRequest());
        return ok();
    }
}
