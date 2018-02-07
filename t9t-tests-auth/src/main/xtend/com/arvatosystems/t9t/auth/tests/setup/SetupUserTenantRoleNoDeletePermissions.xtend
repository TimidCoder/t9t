package com.arvatosystems.t9t.auth.tests.setup

import com.arvatosystems.t9t.base.ITestConnection
import de.jpaw.bonaparte.pojos.api.OperationType
import de.jpaw.bonaparte.pojos.api.auth.Permissionset

// sets up a user who cannot delete anything
class SetupUserTenantRoleNoDeletePermissions extends SetupUserTenantRole {
    private static val ALL_PERMISSIONS_EXCEPT_DELETE = {
        val all = new Permissionset(0xfffff)
        all.remove(OperationType.DELETE)
        all.freeze
        all
    }

    new(ITestConnection dlg) {
        super(dlg)
    }

    override getPermissionset(boolean isMax) {
        return ALL_PERMISSIONS_EXCEPT_DELETE
    }
}
