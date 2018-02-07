package com.arvatosystems.t9t.ssm.be.request

import com.arvatosystems.t9t.base.services.AbstractRequestHandler
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.ssm.request.ClearAllRequest
import de.jpaw.dp.Inject
import org.quartz.Scheduler

class ClearAllRequestHandler extends AbstractRequestHandler<ClearAllRequest> {

    @Inject Scheduler               scheduler

    override execute(RequestContext ctx, ClearAllRequest crudRequest) throws Exception {
        scheduler.clear
        return ok
    }
}
