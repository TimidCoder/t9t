package com.arvatosystems.t9t.auth.tests.setup

import com.arvatosystems.t9t.base.ITestConnection
import de.jpaw.bonaparte.pojos.api.auth.UserLogLevelType

// same as the superclass, but with reduced logging
class SetupUserTenantRoleForBenchmarks extends SetupUserTenantRole {
    new(ITestConnection dlg) {
        super(dlg)
    }

    override getPermissionDTO() {
        return super.permissionDTO => [
            logLevel            = UserLogLevelType.SESSION_ONLY
        ]
    }
}
