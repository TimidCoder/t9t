package com.arvatosystems.t9t.authc.be.api;

import com.arvatosystems.t9t.authc.api.GetUserDataByUserRefRequest;
import com.arvatosystems.t9t.authc.api.GetUserDataResponse;
import com.arvatosystems.t9t.base.services.RequestContext;

public class GetUserDataByUserRefRequestHandler extends AbstractGetUserDataRequestHandler<GetUserDataByUserRefRequest> {

    @Override
    public GetUserDataResponse execute(RequestContext ctx, GetUserDataByUserRefRequest rq) {
        return responseFromDto(resolver.getDTO(rq.getUserRef()));
    }
}
