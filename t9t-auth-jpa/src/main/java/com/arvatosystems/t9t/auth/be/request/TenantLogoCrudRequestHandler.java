package com.arvatosystems.t9t.auth.be.request;

import com.arvatosystems.t9t.auth.TenantLogoDTO;
import com.arvatosystems.t9t.auth.jpa.entities.TenantLogoEntity;
import com.arvatosystems.t9t.auth.jpa.mapping.ITenantLogoDTOMapper;
import com.arvatosystems.t9t.auth.jpa.persistence.ITenantLogoEntityResolver;
import com.arvatosystems.t9t.auth.request.TenantLogoCrudRequest;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.jpa.impl.AbstractCrudModuleCfg42RequestHandler;
import com.arvatosystems.t9t.base.services.RequestContext;

import de.jpaw.dp.Jdp;

public class TenantLogoCrudRequestHandler extends AbstractCrudModuleCfg42RequestHandler<TenantLogoDTO, TenantLogoCrudRequest, TenantLogoEntity> {

    private final ITenantLogoDTOMapper mapper = Jdp.getRequired(ITenantLogoDTOMapper.class);

    private final ITenantLogoEntityResolver resolver = Jdp.getRequired(ITenantLogoEntityResolver.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, TenantLogoCrudRequest params) {
        return execute(ctx, mapper, resolver, params);
    }
}
