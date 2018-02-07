package com.arvatosystems.t9t.auth.services;

import java.util.List;
import java.util.UUID;

import org.joda.time.Instant;

import com.arvatosystems.t9t.auth.AuthModuleCfgDTO;
import com.arvatosystems.t9t.auth.SessionDTO;
import com.arvatosystems.t9t.authc.api.TenantDescription;
import com.arvatosystems.t9t.base.auth.PermissionEntry;
import com.arvatosystems.t9t.base.services.RequestContext;

import de.jpaw.bonaparte.pojos.api.auth.JwtInfo;

public interface IAuthPersistenceAccess {

    //changeMe to correct specification
    public static final AuthModuleCfgDTO DEFAULT_MODULE_CFG = new AuthModuleCfgDTO(
              null
            , 0
            , 0
            , 0
            , 0
            , 0
            , 0
            , 0
            , 0);

    // returns permission entries from the database which are relevant for the user / tenant as specified by the jwtInfo record, and which are relevant for the
    // specified resource. These are all resourceIds which are a substring of resource

    List<PermissionEntry> getAllDBPermissions(JwtInfo jwtInfo);

    AuthIntermediateResult getByApiKey(Instant now, UUID key);
    AuthIntermediateResult getByUserIdAndPassword(Instant now, String userId, String password, String newPassword);

    void storeSession(SessionDTO session);
    List<TenantDescription> getAllTenantsForUser(RequestContext ctx, Long userRef);
}
