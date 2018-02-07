package com.arvatosystems.t9t.server.services;

import com.arvatosystems.t9t.base.auth.AuthenticationRequest;
import com.arvatosystems.t9t.base.auth.AuthenticationResponse;

public interface IAuthenticate {
    AuthenticationResponse login(AuthenticationRequest rq);

    // additional low level functions for per-request authentication (discouraged)
//    JwtInfo authenticate(AuthenticationParameters pw)    ;
}
