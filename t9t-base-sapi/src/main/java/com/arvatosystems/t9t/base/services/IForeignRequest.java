package com.arvatosystems.t9t.base.services;

import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.api.ServiceResponse;

public interface IForeignRequest {
    ServiceResponse execute(RequestContext ctx, RequestParameters rp);
}
