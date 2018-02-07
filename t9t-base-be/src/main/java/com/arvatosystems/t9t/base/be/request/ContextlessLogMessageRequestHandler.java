package com.arvatosystems.t9t.base.be.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.request.ContextlessLogMessageRequest;
import com.arvatosystems.t9t.base.services.AbstractReadOnlyRequestHandler;

import de.jpaw.bonaparte.util.ToStringHelper;

public class ContextlessLogMessageRequestHandler extends AbstractReadOnlyRequestHandler<ContextlessLogMessageRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContextlessLogMessageRequestHandler.class);

    @Override
    public ServiceResponse execute(ContextlessLogMessageRequest rq) {
        LOGGER.info("Log message: {}", rq.getMessage());
        if (rq.getData() != null)
            LOGGER.info("Log data: {}", ToStringHelper.toStringML(rq.getData()));
        return ok();
    }
}
