package com.arvatosystems.t9t.email.be.request

import com.arvatosystems.t9t.base.api.ServiceResponse
import com.arvatosystems.t9t.base.jpa.impl.AbstractCrudModuleCfg42RequestHandler
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.email.EmailModuleCfgDTO
import com.arvatosystems.t9t.email.jpa.entities.EmailModuleCfgEntity
import com.arvatosystems.t9t.email.jpa.mapping.IEmailModuleCfgDTOMapper
import com.arvatosystems.t9t.email.jpa.persistence.IEmailModuleCfgEntityResolver
import com.arvatosystems.t9t.email.request.EmailModuleCfgCrudRequest
import de.jpaw.dp.Inject

class EmailModuleCfgCrudRequestHandler extends AbstractCrudModuleCfg42RequestHandler<EmailModuleCfgDTO, EmailModuleCfgCrudRequest, EmailModuleCfgEntity> {

    @Inject IEmailModuleCfgEntityResolver resolver
    @Inject IEmailModuleCfgDTOMapper mapper

    override public ServiceResponse execute(RequestContext ctx, EmailModuleCfgCrudRequest params) {
        return execute(ctx, mapper, resolver, params);
    }
}
