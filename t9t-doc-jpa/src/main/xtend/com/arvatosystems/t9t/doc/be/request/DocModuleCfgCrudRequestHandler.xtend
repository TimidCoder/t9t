package com.arvatosystems.t9t.doc.be.request

import com.arvatosystems.t9t.base.api.ServiceResponse
import com.arvatosystems.t9t.base.jpa.impl.AbstractCrudModuleCfg42RequestHandler
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.doc.DocModuleCfgDTO
import com.arvatosystems.t9t.doc.jpa.entities.DocModuleCfgEntity
import com.arvatosystems.t9t.doc.jpa.mapping.IDocModuleCfgDTOMapper
import com.arvatosystems.t9t.doc.jpa.persistence.IDocModuleCfgEntityResolver
import com.arvatosystems.t9t.doc.request.DocModuleCfgCrudRequest
import de.jpaw.dp.Inject

class DocModuleCfgCrudRequestHandler extends AbstractCrudModuleCfg42RequestHandler<DocModuleCfgDTO,
        DocModuleCfgCrudRequest, DocModuleCfgEntity> {

    @Inject IDocModuleCfgEntityResolver resolver
    @Inject IDocModuleCfgDTOMapper mapper

    override public ServiceResponse execute(RequestContext ctx, DocModuleCfgCrudRequest params) {
        return execute(ctx, mapper, resolver, params);
    }
}
