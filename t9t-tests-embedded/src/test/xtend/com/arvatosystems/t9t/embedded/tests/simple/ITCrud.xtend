package com.arvatosystems.t9t.embedded.tests.simple

import com.arvatosystems.t9t.auth.RoleKey
import com.arvatosystems.t9t.auth.request.RoleCrudRequest
import com.arvatosystems.t9t.auth.tests.setup.SetupUserTenantRoleNoDeletePermissions
import com.arvatosystems.t9t.embedded.connect.Connection
import de.jpaw.bonaparte.pojos.api.OperationType
import java.util.UUID
import org.junit.Test
import com.arvatosystems.t9t.base.T9tException

class ITCrud {

    @Test
    def public void noDeletePermissionTest() {
        val dlg = new Connection

        val setup = new SetupUserTenantRoleNoDeletePermissions(dlg)

        val newKey = UUID.randomUUID
        setup.createUserTenantRole("crud", newKey, true)

        setup.createRole("bladi")
        dlg.errIO(new RoleCrudRequest => [
            crud            = OperationType.DELETE
            naturalKey      = new RoleKey("bladi")
        ], T9tException.NOT_AUTHORIZED)
    }
}
