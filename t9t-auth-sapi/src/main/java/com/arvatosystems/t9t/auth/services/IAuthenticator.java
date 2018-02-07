package com.arvatosystems.t9t.auth.services;

import de.jpaw.bonaparte.pojos.api.auth.JwtInfo;

// method to create a new signed JWT
public interface IAuthenticator {
    String doSign(JwtInfo info);
}
