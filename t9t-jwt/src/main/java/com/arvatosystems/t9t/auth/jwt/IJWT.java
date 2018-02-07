package com.arvatosystems.t9t.auth.jwt;

import de.jpaw.bonaparte.pojos.api.auth.JwtInfo;

public interface IJWT {

    /** Creates a signed base64 encoded String for a given JwtInfo object.
     * The method will add the iat field as well as (if an expiry time has been specified), the exp field. */
    String sign(JwtInfo info, Long expiresInSeconds, String algorithmOverride);

    /** Decodes a base64 encoded JWT. */
    JwtInfo decode(final String token);
}
