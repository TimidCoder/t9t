package com.arvatosystems.t9t.base.be.request;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.request.PingRequest;
import com.arvatosystems.t9t.base.services.AbstractReadOnlyRequestHandler;


/**
 * The default implementation responsible for handling all incoming requests of type {@link PingRequest}.
 */
public class PingRequestHandler extends AbstractReadOnlyRequestHandler<PingRequest> {

    @Override
    public ServiceResponse execute(PingRequest pingRequest) {
        return ok();
    }
}
