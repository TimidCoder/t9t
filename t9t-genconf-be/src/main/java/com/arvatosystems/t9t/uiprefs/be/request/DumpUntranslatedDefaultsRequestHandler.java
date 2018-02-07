package com.arvatosystems.t9t.uiprefs.be.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.uiprefs.request.DumpUntranslatedDefaultsRequest;

public class DumpUntranslatedDefaultsRequestHandler extends AbstractRequestHandler<DumpUntranslatedDefaultsRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DumpUntranslatedDefaultsRequestHandler.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, DumpUntranslatedDefaultsRequest request) throws Exception {
        StringBuilder buff = new StringBuilder(10000);
        for (String s: GridConfigRequestHandler.UNTRANSLATED_DEFAULTS.keySet()) {
            buff.append(s);
            buff.append("=\n");
        }
        LOGGER.info("Untranslated defaults are:\n{}", buff.toString());
        return ok();
    }
}
