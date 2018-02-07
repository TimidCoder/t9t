package com.arvatosystems.t9t.auth.be.vertx

import de.jpaw.bonaparte.pojos.api.auth.JwtInfo
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.AuthProvider
import io.vertx.ext.auth.User

public class T9tVertxUser implements User {
    private final String jwtToken;          // encoded form, without "Bearer" prefix
    private final JwtInfo info;             // decoded data in map form
    private final JsonObject principal;     // token + decoded map

    public new (String jwtToken, JwtInfo info) {
        this.jwtToken = jwtToken;
        this.info = info
        principal = new JsonObject(#{ "jwt" -> jwtToken, "info" -> info })
    }

    def isOrWasValid() {
        return info !== null && jwtToken !== null
    }
    def isStillValid() {
        info.expiresAt === null || info.expiresAt.isAfterNow
    }
    def getUserId() {
        return info?.userId
    }
    override clearCache() {
    }

    override principal() {
        return principal;
    }

    override isAuthorised(String authority, Handler<AsyncResult<Boolean>> resultHandler) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub")
    }

    override setAuthProvider(AuthProvider authProvider) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub")
    }
}
