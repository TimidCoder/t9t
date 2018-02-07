package com.arvatosystems.t9t.base.be.request;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.request.ProcessEventRequest;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.IEventHandler;
import com.arvatosystems.t9t.base.services.RequestContext;
import de.jpaw.dp.Jdp;

public class ProcessEventRequestHandler extends AbstractRequestHandler<ProcessEventRequest> {
    @Override
    public ServiceResponse execute(final RequestContext ctx, final ProcessEventRequest rq) {
        final IEventHandler handler = Jdp.getRequired(IEventHandler.class, rq.getEventHandlerQualifier());
        final int returnCode = handler.execute(ctx, rq.getEventData());
        return this.ok(returnCode);
    }
}
