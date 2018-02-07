package com.arvatosystems.t9t.auth.be.jwt;

import com.arvatosystems.t9t.auth.jwt.IJWT;
import com.arvatosystems.t9t.auth.services.IAuthenticator;

import de.jpaw.bonaparte.pojos.api.auth.JwtInfo;
import de.jpaw.dp.Dependent;
import de.jpaw.dp.Fallback;
import de.jpaw.dp.Jdp;

@Fallback
@Dependent
public class Authenticator implements IAuthenticator {

    private final IJWT generator = Jdp.getRequired(IJWT.class); // JWT.createDefaultJwt();

    @Override
    public String doSign(JwtInfo info) {
        return generator.sign(info, 12L * 60 * 60, null);
    }
}
