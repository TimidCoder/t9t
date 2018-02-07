package com.arvatosystems.t9t.base.be.request;

import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.request.BatchRequest;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.IExecutor;
import com.arvatosystems.t9t.base.services.RequestContext;

import de.jpaw.dp.Jdp;
import de.jpaw.util.ApplicationException;

public class BatchRequestHandler extends AbstractRequestHandler<BatchRequest> {

    // @Inject
    protected final IExecutor messaging = Jdp.getRequired(IExecutor.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, BatchRequest request) {
        for (RequestParameters r : request.getCommands()) {
            ServiceResponse resp = messaging.executeSynchronous(ctx, r);
            switch (resp.getReturnCode() / ApplicationException.CLASSIFICATION_FACTOR) {
            case ApplicationException.SUCCESS:
                break; // continue processing
            case ApplicationException.CL_DENIED:
                if (request.getAllowNo())
                    break; // continue processing
                else
                    return resp; // stop
            default:
                // any other error
                return resp;
            }
            ctx.incrementProgress();  // one per request done
        }
        return new ServiceResponse();
    }
}
