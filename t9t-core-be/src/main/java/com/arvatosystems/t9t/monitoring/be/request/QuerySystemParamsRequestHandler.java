package com.arvatosystems.t9t.monitoring.be.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.monitoring.request.QuerySystemParamsRequest;
import com.arvatosystems.t9t.monitoring.request.QuerySystemParamsResponse;

public class QuerySystemParamsRequestHandler extends AbstractRequestHandler<QuerySystemParamsRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuerySystemParamsRequestHandler.class);

    @Override
    public QuerySystemParamsResponse execute(RequestContext ctx, QuerySystemParamsRequest rq) {
        final Runtime rt = Runtime.getRuntime();
        final QuerySystemParamsResponse rs = new QuerySystemParamsResponse();
        rs.setCurrentTimeMillis(System.currentTimeMillis());
        rs.setAvailableProcessors(rt.availableProcessors());
        rs.setTotalMemory(rt.totalMemory());
        rs.setFreeMemory(rt.freeMemory());
        rs.setMaxMemory(rt.maxMemory());
        LOGGER.info("Runtime parameters queried: {}", rs);
        return rs;
    }
}
