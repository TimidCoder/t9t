package com.arvatosystems.t9t.base.be.request;

import com.arvatosystems.t9t.base.T9tConstants;
import com.arvatosystems.t9t.base.be.execution.RequestContextScope;
import com.arvatosystems.t9t.base.request.ProcessStatusRequest;
import com.arvatosystems.t9t.base.request.ProcessStatusResponse;
import com.arvatosystems.t9t.base.services.AbstractReadOnlyRequestHandler;
import com.arvatosystems.t9t.base.services.RequestContext;

import de.jpaw.dp.Jdp;

/**
 * A technical Request handler which is used to pass exceptions thrown in outer
 * transport layers through appropriate database logging and response message
 * translation.
 */
public class ProcessStatusRequestHandler extends AbstractReadOnlyRequestHandler<ProcessStatusRequest> {
    private final RequestContextScope requestContextScope = Jdp.getRequired(RequestContextScope.class);

    @Override
    public ProcessStatusResponse execute(final RequestContext ctx, final ProcessStatusRequest rq) {
        final ProcessStatusResponse resp = new ProcessStatusResponse();
        if (!T9tConstants.GLOBAL_TENANT_ID.equals(ctx.tenantId)) {
            rq.setTenantId(ctx.tenantId);
        }
        resp.setProcesses(this.requestContextScope.getProcessStatus(rq, ctx.executionStart));
        return resp;
    }
}
