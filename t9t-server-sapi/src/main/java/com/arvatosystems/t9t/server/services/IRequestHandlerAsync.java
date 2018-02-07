package com.arvatosystems.t9t.server.services;

import java.util.function.Consumer;

import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.server.InternalHeaderParameters;

public interface IRequestHandlerAsync<T extends RequestParameters> {
    boolean isReadOnly();
    void execute(InternalHeaderParameters ihdr, T rq, Consumer<ServiceResponse> callback);
}
