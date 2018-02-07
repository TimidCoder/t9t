package com.arvatosystems.t9t.auth.mocks

import com.arvatosystems.t9t.server.services.IAuthorize
import de.jpaw.annotations.AddLogger
import de.jpaw.bonaparte.pojos.api.auth.JwtInfo
import de.jpaw.bonaparte.pojos.api.auth.Permissionset
import de.jpaw.dp.Fallback
import de.jpaw.dp.Singleton
import com.arvatosystems.t9t.base.auth.PermissionEntry
import de.jpaw.bonaparte.pojos.api.OperationType
import com.arvatosystems.t9t.base.auth.PermissionType

@Fallback
@Singleton
@AddLogger
class AuthorizationMock implements IAuthorize {
    private static final Permissionset NO_PERMISSIONS = new Permissionset

    override getPermissions(JwtInfo jwtInfo, PermissionType permissionType, String resource) {
        LOGGER.debug("Permission requested for user {}, tenant {}, type {}, resource {}", jwtInfo.userId, jwtInfo.tenantId, permissionType, resource);

        return jwtInfo.permissionsMin ?: NO_PERMISSIONS
    }

    override getAllPermissions(JwtInfo jwtInfo, PermissionType permissionType) {
        LOGGER.debug("Full permission list requested for user {}, tenant {}, type {}", jwtInfo.userId, jwtInfo.tenantId, permissionType);
        if (permissionType == PermissionType.BACKEND)
            return #[ new PermissionEntry("B.testRequest", Permissionset.of(OperationType.EXECUTE))]
        else
            return #[]
    }
}
