package com.arvatosystems.t9t.monitoring.be.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.monitoring.request.PerformGCRequest;

public class PerformGCRequestHandler extends AbstractRequestHandler<PerformGCRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PerformGCRequestHandler.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, PerformGCRequest rq) {
        LOGGER.info("Full GC requested - starting");
        System.gc();
        LOGGER.info("Full GC finished");
        return ok();
    }
}
