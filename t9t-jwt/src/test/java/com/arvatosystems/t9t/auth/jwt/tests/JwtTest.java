package com.arvatosystems.t9t.auth.jwt.tests;


import org.joda.time.Instant;
import org.junit.Assert;
import org.junit.Test;

import com.arvatosystems.t9t.auth.jwt.IJWT;
import com.arvatosystems.t9t.auth.jwt.JWT;

import de.jpaw.bonaparte.pojos.api.auth.JwtInfo;

public class JwtTest {

    @Test
    public void checkSign() throws Exception {
        JWT jwt = JWT.createJwt(IJWT.class.getResourceAsStream("/mykeystore.jceks"), "xyzzy5");
        JwtInfo info = new JwtInfo();

        String encoded = jwt.sign(info, 10L, null);
        long now = System.currentTimeMillis();


        JwtInfo info2 = jwt.decode(encoded);

        Instant whenSigned = info.getIssuedAt();  // the system timestamp written back

        Assert.assertNotNull(whenSigned);
        Assert.assertTrue(now >= whenSigned.getMillis());
        Assert.assertEquals(whenSigned, info2.getIssuedAt());
    }
}
