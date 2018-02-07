package com.arvatosystems.t9t.auth.be.request;

import com.arvatosystems.t9t.auth.UserDTO;
import com.arvatosystems.t9t.auth.UserRef;
import com.arvatosystems.t9t.auth.request.UserCrudRequest;
import com.arvatosystems.t9t.auth.services.IUserResolver;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.be.impl.AbstractCrudSurrogateKeyBERequestHandler;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.services.RequestContext;

import de.jpaw.dp.Jdp;

public class UserCrudRequestHandler extends AbstractCrudSurrogateKeyBERequestHandler<UserRef, UserDTO, FullTrackingWithVersion, UserCrudRequest> {

    protected final IUserResolver resolver = Jdp.getRequired(IUserResolver.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, UserCrudRequest crudRequest) {
        return execute(ctx, crudRequest, resolver);
    }
}
