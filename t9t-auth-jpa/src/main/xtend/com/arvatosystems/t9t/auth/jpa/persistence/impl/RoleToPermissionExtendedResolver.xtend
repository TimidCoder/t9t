package com.arvatosystems.t9t.auth.jpa.persistence.impl

import com.arvatosystems.t9t.auth.RoleToPermissionInternalKey
import com.arvatosystems.t9t.auth.RoleToPermissionKey
import com.arvatosystems.t9t.auth.RoleToPermissionRef
import com.arvatosystems.t9t.auth.jpa.persistence.IRoleEntityResolver
import de.jpaw.dp.Inject
import de.jpaw.dp.Singleton
import de.jpaw.dp.Specializes

@Specializes
@Singleton
class RoleToPermissionExtendedResolver extends RoleToPermissionEntityResolver {

    @Inject IRoleEntityResolver roleResolver;

    override protected RoleToPermissionRef resolveNestedRefs(RoleToPermissionRef ref) {
        if (ref instanceof RoleToPermissionKey) {
            return new RoleToPermissionInternalKey => [
                roleRef      = roleResolver.getRef(ref.roleRef, false)
                permissionId = ref.permissionId
                tenantRef    = sharedTenantRef
            ]
        }
        return super.resolveNestedRefs(ref);
    }
}
