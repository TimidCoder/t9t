package com.arvatosystems.t9t.auth.be.request

import com.arvatosystems.t9t.auth.AuthModuleCfgDTO
import com.arvatosystems.t9t.auth.jpa.entities.AuthModuleCfgEntity
import com.arvatosystems.t9t.auth.jpa.mapping.IAuthModuleCfgDTOMapper
import com.arvatosystems.t9t.auth.jpa.persistence.IAuthModuleCfgEntityResolver
import com.arvatosystems.t9t.auth.request.AuthModuleCfgCrudRequest
import com.arvatosystems.t9t.base.api.ServiceResponse
import com.arvatosystems.t9t.base.jpa.impl.AbstractCrudModuleCfg42RequestHandler
import com.arvatosystems.t9t.base.services.RequestContext
import de.jpaw.dp.Inject

class AuthModuleCfgCrudRequestHandler extends AbstractCrudModuleCfg42RequestHandler<AuthModuleCfgDTO,
        AuthModuleCfgCrudRequest, AuthModuleCfgEntity> {

    @Inject IAuthModuleCfgEntityResolver resolver
    @Inject IAuthModuleCfgDTOMapper mapper

    override public ServiceResponse execute(RequestContext ctx, AuthModuleCfgCrudRequest params) {
        return execute(ctx, mapper, resolver, params);
    }
}
