package com.arvatosystems.t9t.base.be.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.request.LogMessageRequest;
import com.arvatosystems.t9t.base.services.AbstractReadOnlyRequestHandler;
import com.arvatosystems.t9t.base.services.RequestContext;

import de.jpaw.bonaparte.util.ToStringHelper;

public class LogMessageRequestHandler extends AbstractReadOnlyRequestHandler<LogMessageRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogMessageRequestHandler.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, LogMessageRequest rq) {
        LOGGER.info("Log message: {}", rq.getMessage());
        ctx.statusText = rq.getMessage();
        if (rq.getData() != null)
            LOGGER.info("Log data: {}", ToStringHelper.toStringML(rq.getData()));
        return ok();
    }
}
