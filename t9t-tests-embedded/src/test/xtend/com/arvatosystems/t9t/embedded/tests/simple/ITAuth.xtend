package com.arvatosystems.t9t.embedded.tests.simple

import com.arvatosystems.t9t.base.request.PingRequest
import com.arvatosystems.t9t.embedded.connect.Connection
import java.util.UUID
import org.junit.Test

class ITAuth {
    static final UUID DUMMY_API_KEY = UUID.fromString("896d22d1-a332-438e-b2f8-2a539c29b8ca")

    @Test
    def public void pingTest() {
        val dlg = new Connection
        dlg.switchUser(DUMMY_API_KEY)
        dlg.okIO(new PingRequest)
    }
    @Test
    def public void ping2Test() {
        val dlg = new Connection(DUMMY_API_KEY)

        dlg.okIO(new PingRequest)
    }
}
