package com.arvatosystems.t9t.auth.be.impl

import com.arvatosystems.t9t.auth.jwt.IJWT
import com.arvatosystems.t9t.auth.jwt.JWT
import de.jpaw.annotations.AddLogger
import de.jpaw.bonaparte.pojos.api.auth.JwtInfo
import de.jpaw.dp.Singleton

@AddLogger
@Singleton
class JWTPool implements IJWT {

    private val pool = new ThreadLocal<IJWT>();

    // for injections where you are sure you have per thread scope as well
    def private get() {
        val jwt = pool.get();
        if (jwt !== null) {
            return jwt
        }
        LOGGER.info("Creating a new JWT for thread {}", Thread.currentThread.name)
        val newJwt = JWT.createDefaultJwt
        pool.set(newJwt)
        return newJwt
    }
    override decode(String token) {
        return get().decode(token)
    }

    override sign(JwtInfo info, Long expiresInSeconds, String algorithmOverride) {
        return get().sign(info, expiresInSeconds, algorithmOverride)
    }
}
