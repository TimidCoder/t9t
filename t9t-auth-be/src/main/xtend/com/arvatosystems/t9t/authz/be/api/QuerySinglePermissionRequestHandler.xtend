package com.arvatosystems.t9t.authz.be.api

import com.arvatosystems.t9t.authz.api.QuerySinglePermissionRequest
import com.arvatosystems.t9t.authz.api.QuerySinglePermissionResponse
import com.arvatosystems.t9t.base.services.AbstractReadOnlyRequestHandler
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.server.services.IAuthorize
import de.jpaw.annotations.AddLogger
import de.jpaw.dp.Inject

@AddLogger
class QuerySinglePermissionRequestHandler extends AbstractReadOnlyRequestHandler<QuerySinglePermissionRequest> {

    @Inject IAuthorize authorizer

    override QuerySinglePermissionResponse execute(RequestContext ctx, QuerySinglePermissionRequest rq) {
        return new QuerySinglePermissionResponse => [
            permissions = authorizer.getPermissions(ctx.internalHeaderParameters.jwtInfo, rq.permissionType, rq.resourceId)
        ]
    }
}
