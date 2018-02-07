package com.arvatosystems.t9t.authc.be.api

import com.arvatosystems.t9t.auth.services.ITenantLogoDtoResolver
import com.arvatosystems.t9t.authc.api.GetTenantLogoRequest
import com.arvatosystems.t9t.authc.api.GetTenantLogoResponse
import com.arvatosystems.t9t.base.services.AbstractReadOnlyRequestHandler
import com.arvatosystems.t9t.base.services.RequestContext
import de.jpaw.annotations.AddLogger
import de.jpaw.dp.Inject

@AddLogger
class GetTenantLogoRequestHandler extends AbstractReadOnlyRequestHandler<GetTenantLogoRequest> {

    @Inject ITenantLogoDtoResolver tenantLogoResolver

    override GetTenantLogoResponse execute(RequestContext ctx, GetTenantLogoRequest rq) {
        val r = new GetTenantLogoResponse
        r.tenantLogo = tenantLogoResolver.moduleConfiguration.logo
        return r
    }
}
