package com.arvatosystems.t9t.authc.be.api

import com.arvatosystems.t9t.auth.services.ITenantResolver
import com.arvatosystems.t9t.authc.api.GetCurrentJwtRequest
import com.arvatosystems.t9t.base.auth.AuthenticationResponse
import com.arvatosystems.t9t.base.services.AbstractReadOnlyRequestHandler
import com.arvatosystems.t9t.base.services.RequestContext
import de.jpaw.annotations.AddLogger
import de.jpaw.dp.Inject

@AddLogger
class GetCurrentJwtRequestHandler extends AbstractReadOnlyRequestHandler<GetCurrentJwtRequest> {

    @Inject ITenantResolver        tenantResolver

    override AuthenticationResponse execute(RequestContext ctx, GetCurrentJwtRequest rq) {
        val tenantDTO            = tenantResolver.getDTO(ctx.tenantRef)

        val authResp             = new AuthenticationResponse
        authResp.jwtInfo         = ctx.internalHeaderParameters.jwtInfo
        authResp.encodedJwt      = ctx.internalHeaderParameters.encodedJwt
        authResp.tenantName      = tenantDTO.name
        return authResp
    }
}
