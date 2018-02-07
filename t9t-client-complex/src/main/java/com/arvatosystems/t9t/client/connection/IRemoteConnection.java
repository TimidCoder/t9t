package com.arvatosystems.t9t.client.connection;

import java.util.List;

import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.auth.AuthenticationRequest;

public interface IRemoteConnection {
    /** execute a single (regular) request for an authenticated context. */
    ServiceResponse execute(String authenticationHeader, RequestParameters rp);

    /** execute one or more (regular) requests for an authenticated context.
     * If the list size is more than one, a batch request will be constructed. */
    ServiceResponse execute(String authenticationHeader, List<RequestParameters> rpList);

    /** authenticate. */
    ServiceResponse executeAuthenticationRequest(AuthenticationRequest rp);
}
