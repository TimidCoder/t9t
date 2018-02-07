package com.arvatosystems.t9t.base;

import java.util.Map;

import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.api.ServiceResponse;

public interface IConnection {
    public ServiceResponse executeRequest(RequestParameters rq, String requestUri, String authentication,
            Map<String, String> extraHttpParams);
    public ServiceResponse executeRequest(RequestParameters rq, String requestUri, String encodedJwt);
}
