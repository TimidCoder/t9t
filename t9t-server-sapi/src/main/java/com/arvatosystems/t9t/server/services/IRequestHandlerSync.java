package com.arvatosystems.t9t.server.services;

import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.server.InternalHeaderParameters;

public interface IRequestHandlerSync<T extends RequestParameters> {
    boolean isReadOnly();
    ServiceResponse execute(InternalHeaderParameters ihdr, T rq);
}
