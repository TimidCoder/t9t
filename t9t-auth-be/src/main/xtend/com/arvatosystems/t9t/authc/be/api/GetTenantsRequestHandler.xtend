package com.arvatosystems.t9t.authc.be.api

import com.arvatosystems.t9t.auth.services.IAuthPersistenceAccess
import com.arvatosystems.t9t.authc.api.GetTenantsRequest
import com.arvatosystems.t9t.authc.api.GetTenantsResponse
import com.arvatosystems.t9t.base.services.AbstractReadOnlyRequestHandler
import com.arvatosystems.t9t.base.services.RequestContext
import de.jpaw.annotations.AddLogger
import de.jpaw.dp.Inject

@AddLogger
class GetTenantsRequestHandler extends AbstractReadOnlyRequestHandler<GetTenantsRequest> {

    @Inject IAuthPersistenceAccess   authPersistenceAccess

    override GetTenantsResponse execute(RequestContext ctx, GetTenantsRequest rq) {
        val it = new GetTenantsResponse
        tenants = authPersistenceAccess.getAllTenantsForUser(ctx, ctx.userRef)
        LOGGER.debug("Got {} tenants", tenants.size)
        return it
    }
}
