package com.arvatosystems.t9t.authc.be.api;

import com.arvatosystems.t9t.auth.UserKey;
import com.arvatosystems.t9t.authc.api.GetUserDataByUserIdRequest;
import com.arvatosystems.t9t.authc.api.GetUserDataResponse;
import com.arvatosystems.t9t.base.services.RequestContext;

public class GetUserDataByUserIdRequestHandler extends AbstractGetUserDataRequestHandler<GetUserDataByUserIdRequest> {

    @Override
    public GetUserDataResponse execute(RequestContext ctx, GetUserDataByUserIdRequest rq) {
        return responseFromDto(resolver.getDTO(new UserKey(rq.getUserId())));
    }
}
