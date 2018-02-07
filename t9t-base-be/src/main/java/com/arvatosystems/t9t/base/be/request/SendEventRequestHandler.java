package com.arvatosystems.t9t.base.be.request;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.request.PublishEventRequest;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.IExecutor;
import com.arvatosystems.t9t.base.services.RequestContext;

import de.jpaw.dp.Jdp;

public class SendEventRequestHandler extends AbstractRequestHandler<PublishEventRequest> {
    private final IExecutor eventDispatcher = Jdp.getRequired(IExecutor.class);

    @Override
    public ServiceResponse execute(final RequestContext ctx, final PublishEventRequest rq) {
        eventDispatcher.sendEvent(ctx, rq.getEventData());
        return ok();
    }
}
