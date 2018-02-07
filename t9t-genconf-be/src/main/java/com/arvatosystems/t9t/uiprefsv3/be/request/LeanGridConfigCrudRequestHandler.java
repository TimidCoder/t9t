package com.arvatosystems.t9t.uiprefsv3.be.request;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.be.impl.AbstractCrudSurrogateKeyBERequestHandler;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.uiprefsv3.request.LeanGridConfigCrudRequest;
import com.arvatosystems.t9t.uiprefsv3.LeanGridConfigDTO;
import com.arvatosystems.t9t.uiprefsv3.LeanGridConfigRef;
import com.arvatosystems.t9t.uiprefsv3.services.ILeanGridConfigResolver;

import de.jpaw.dp.Jdp;

public class LeanGridConfigCrudRequestHandler extends AbstractCrudSurrogateKeyBERequestHandler<LeanGridConfigRef, LeanGridConfigDTO, FullTrackingWithVersion, LeanGridConfigCrudRequest> {

    // @Inject
    protected final ILeanGridConfigResolver resolver = Jdp.getRequired(ILeanGridConfigResolver.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, LeanGridConfigCrudRequest request) throws Exception {
        return execute(ctx, request, resolver);
    }
}
