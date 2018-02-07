package com.arvatosystems.t9t.authc.be.api

import com.arvatosystems.t9t.auth.be.impl.AuthResponseUtil
import com.arvatosystems.t9t.auth.hooks.IJwtEnrichment
import com.arvatosystems.t9t.auth.services.IAuthPersistenceAccess
import com.arvatosystems.t9t.authc.api.SwitchLanguageRequest
import com.arvatosystems.t9t.base.T9tException
import com.arvatosystems.t9t.base.auth.AuthenticationResponse
import com.arvatosystems.t9t.base.services.AbstractRequestHandler
import com.arvatosystems.t9t.base.services.RequestContext
import de.jpaw.annotations.AddLogger
import de.jpaw.dp.Inject

@AddLogger
class SwitchLanguageRequestHandler extends AbstractRequestHandler<SwitchLanguageRequest> {

    @Inject IAuthPersistenceAccess  authPersistenceAccess
    @Inject AuthResponseUtil        authResponseUtil
    @Inject IJwtEnrichment          jwtEnrichment

    override AuthenticationResponse execute(RequestContext ctx, SwitchLanguageRequest rq) {
        val jwt       = ctx.internalHeaderParameters.jwtInfo.ret$MutableClone(false, false)
        val tenants   = authPersistenceAccess.getAllTenantsForUser(ctx, ctx.userRef)
        val newTenant = tenants.findFirst[tenantId == ctx.tenantId]
        if (newTenant === null)
            throw new T9tException(T9tException.NOT_AUTHORIZED);  // permission was revoked

        jwt.locale               = rq.language  // transfer new language into JWT
        jwt.zoneinfo             = ctx.internalHeaderParameters.jwtInfo.zoneinfo  // keep existing zoneinfo

        // have to reset any timestamp fields. These will be created new
        jwt.issuedAt             = null
        jwt.expiresAt            = null
        jwt.notBefore            = null
        jwtEnrichment.enrichJwt(jwt, ctx.internalHeaderParameters.jwtInfo)

        val authResp             = new AuthenticationResponse
        authResp.jwtInfo         = jwt
        authResp.encodedJwt      = authResponseUtil.authResponseFromJwt(jwt, null, ctx.internalHeaderParameters.jwtInfo)
        authResp.tenantName      = newTenant.name
        authResp.tenantNotUnique = tenants.size > 1
        return authResp
    }
}
