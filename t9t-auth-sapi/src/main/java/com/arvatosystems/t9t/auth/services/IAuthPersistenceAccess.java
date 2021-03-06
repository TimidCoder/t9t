/*
 * Copyright (c) 2012 - 2018 Arvato Systems GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
