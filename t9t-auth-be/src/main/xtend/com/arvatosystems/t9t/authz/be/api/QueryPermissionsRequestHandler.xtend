package com.arvatosystems.t9t.authz.be.api

import com.arvatosystems.t9t.authz.api.QueryPermissionsRequest
import com.arvatosystems.t9t.authz.api.QueryPermissionsResponse
import com.arvatosystems.t9t.base.services.AbstractReadOnlyRequestHandler
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.server.services.IAuthorize
import de.jpaw.annotations.AddLogger
import de.jpaw.dp.Inject

@AddLogger
class QueryPermissionsRequestHandler extends AbstractReadOnlyRequestHandler<QueryPermissionsRequest> {

    @Inject IAuthorize authorizer

    override QueryPermissionsResponse execute(RequestContext ctx, QueryPermissionsRequest rq) {
        return new QueryPermissionsResponse => [
            permissions = authorizer.getAllPermissions(ctx.internalHeaderParameters.jwtInfo, rq.permissionType)
        ]
    }
}
