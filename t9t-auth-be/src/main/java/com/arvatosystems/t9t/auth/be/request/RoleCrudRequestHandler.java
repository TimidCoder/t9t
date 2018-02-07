package com.arvatosystems.t9t.auth.be.request;

import com.arvatosystems.t9t.auth.RoleDTO;
import com.arvatosystems.t9t.auth.RoleRef;
import com.arvatosystems.t9t.auth.request.RoleCrudRequest;
import com.arvatosystems.t9t.auth.services.IRoleResolver;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.be.impl.AbstractCrudSurrogateKeyBERequestHandler;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.services.RequestContext;

import de.jpaw.dp.Jdp;

public class RoleCrudRequestHandler extends AbstractCrudSurrogateKeyBERequestHandler<RoleRef, RoleDTO, FullTrackingWithVersion, RoleCrudRequest> {

    protected final IRoleResolver resolver = Jdp.getRequired(IRoleResolver.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, RoleCrudRequest crudRequest) {
        return execute(ctx, crudRequest, resolver);
    }
}
