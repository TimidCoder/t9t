package com.arvatosystems.t9t.base.be.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.T9tException;
import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.RequestContext;

/** request handler which is called in case no suitable request handler has been found.
 * It receives the request parameters intended for the real request handler.
 *
 * @author Michael Bischoff
 *
 */
public class NoHandlerPresentRequestHandler extends AbstractRequestHandler<RequestParameters> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoHandlerPresentRequestHandler.class);
    private final String reason;

    public NoHandlerPresentRequestHandler(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean isReadOnly(RequestParameters request) {
        return true;
    }

    @Override
    public ServiceResponse execute(RequestContext ctx, RequestParameters request) {
        LOGGER.error("No request handler found for request class {} and tenant {}, or instantiation failed", request.ret$PQON(), ctx.tenantId);
        return error(T9tException.REQUEST_HANDLER_NOT_FOUND, reason);
    }
}
