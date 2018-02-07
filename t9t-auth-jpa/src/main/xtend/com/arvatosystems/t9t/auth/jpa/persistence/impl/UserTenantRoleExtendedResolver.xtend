package com.arvatosystems.t9t.auth.jpa.persistence.impl

import com.arvatosystems.t9t.auth.UserTenantRoleKey
import com.arvatosystems.t9t.auth.UserTenantRoleRef
import com.arvatosystems.t9t.auth.jpa.persistence.IRoleEntityResolver
import com.arvatosystems.t9t.auth.jpa.persistence.IUserEntityResolver
import de.jpaw.dp.Inject
import de.jpaw.dp.Singleton
import de.jpaw.dp.Specializes
import com.arvatosystems.t9t.auth.UserTenantRoleInternalKey

@Specializes
@Singleton
class UserTenantRoleExtendedResolver extends UserTenantRoleEntityResolver {

    @Inject IRoleEntityResolver roleResolver;
    @Inject IUserEntityResolver userResolver;

    override protected UserTenantRoleRef resolveNestedRefs(UserTenantRoleRef ref) {
        if (ref instanceof UserTenantRoleKey) {
            return new UserTenantRoleInternalKey => [
                userRef   = userResolver.getRef(ref.userRef, false)
                roleRef   = roleResolver.getRef(ref.roleRef, false)
                tenantRef = sharedTenantRef
            ]
        }
        return super.resolveNestedRefs(ref);
    }
}
