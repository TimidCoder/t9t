package com.arvatosystems.t9t.voice.be.request

import com.arvatosystems.t9t.base.api.ServiceResponse
import com.arvatosystems.t9t.base.jpa.impl.AbstractCrudModuleCfg42RequestHandler
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.voice.VoiceModuleCfgDTO
import com.arvatosystems.t9t.voice.jpa.entities.VoiceModuleCfgEntity
import com.arvatosystems.t9t.voice.jpa.mapping.IVoiceModuleCfgDTOMapper
import com.arvatosystems.t9t.voice.jpa.persistence.IVoiceModuleCfgEntityResolver
import com.arvatosystems.t9t.voice.request.VoiceModuleCfgCrudRequest
import de.jpaw.dp.Inject

class VoiceModuleCfgCrudRequestHandler extends AbstractCrudModuleCfg42RequestHandler<VoiceModuleCfgDTO,
        VoiceModuleCfgCrudRequest, VoiceModuleCfgEntity> {

    @Inject IVoiceModuleCfgEntityResolver resolver
    @Inject IVoiceModuleCfgDTOMapper mapper

    override public ServiceResponse execute(RequestContext ctx, VoiceModuleCfgCrudRequest params) {
        return execute(ctx, mapper, resolver, params);
    }
}
