package com.arvatosystems.t9t.authc.be.api;

import com.arvatosystems.t9t.authc.api.GetMyUserDataRequest;
import com.arvatosystems.t9t.authc.api.GetUserDataResponse;
import com.arvatosystems.t9t.base.services.RequestContext;

public class GetMyUserDataRequestHandler extends AbstractGetUserDataRequestHandler<GetMyUserDataRequest> {

    @Override
    public GetUserDataResponse execute(RequestContext ctx, GetMyUserDataRequest rq) {
        return responseFromDto(resolver.getDTO(ctx.userRef));
    }
}
