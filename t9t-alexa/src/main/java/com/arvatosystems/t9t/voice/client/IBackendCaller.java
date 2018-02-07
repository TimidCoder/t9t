package com.arvatosystems.t9t.voice.client;

import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.voice.request.ProvideSessionResponse;
import java.util.UUID;

public interface IBackendCaller {
    ProvideSessionResponse getSession(String applicationId, String userId, String locale);
    ServiceResponse executeWithApiKey(UUID apiKey, RequestParameters rq);
}
