package com.arvatosystems.t9t.auth.be.request;

import com.arvatosystems.t9t.auth.TenantDTO;
import com.arvatosystems.t9t.auth.TenantRef;
import com.arvatosystems.t9t.auth.request.TenantCrudRequest;
import com.arvatosystems.t9t.auth.services.ITenantResolver;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.be.impl.AbstractCrudSurrogateKeyBERequestHandler;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.services.RequestContext;

import de.jpaw.dp.Jdp;

public class TenantCrudRequestHandler extends AbstractCrudSurrogateKeyBERequestHandler<TenantRef, TenantDTO, FullTrackingWithVersion, TenantCrudRequest> {

    protected final ITenantResolver resolver = Jdp.getRequired(ITenantResolver.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, TenantCrudRequest crudRequest) {
        return execute(ctx, crudRequest, resolver);
    }
}
