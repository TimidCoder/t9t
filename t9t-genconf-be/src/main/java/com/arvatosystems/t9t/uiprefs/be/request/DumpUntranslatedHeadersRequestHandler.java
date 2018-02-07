package com.arvatosystems.t9t.uiprefs.be.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.uiprefs.request.DumpUntranslatedHeadersRequest;

public class DumpUntranslatedHeadersRequestHandler extends AbstractRequestHandler<DumpUntranslatedHeadersRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DumpUntranslatedHeadersRequestHandler.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, DumpUntranslatedHeadersRequest request) throws Exception {
        StringBuilder buff = new StringBuilder(10000);
        for (String s: GridConfigRequestHandler.UNTRANSLATED_HEADERS.keySet()) {
            buff.append(s);
            buff.append("=\n");
        }
        LOGGER.info("Untranslated headers are:\n{}", buff.toString());
        return ok();
    }
}
