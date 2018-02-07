package com.arvatosystems.t9t.base.be.request;

import com.arvatosystems.t9t.base.request.LogJdbcPoolRequest;
import com.arvatosystems.t9t.base.request.LogJdbcPoolResponse;
import com.arvatosystems.t9t.base.services.AbstractReadOnlyRequestHandler;
import com.arvatosystems.t9t.base.services.IJdbcConnectionProvider;

import de.jpaw.dp.Jdp;

public class LogJdbcPoolRequestHandler extends AbstractReadOnlyRequestHandler<LogJdbcPoolRequest> {
    // @Inject
    private final IJdbcConnectionProvider jdbcProvider = Jdp.getRequired(IJdbcConnectionProvider.class, "independent");

    @Override
    public LogJdbcPoolResponse execute(LogJdbcPoolRequest rq) {
        LogJdbcPoolResponse resp = new LogJdbcPoolResponse();
        resp.setCounts(jdbcProvider.checkHealth());
        return resp;
    }
}
