package com.arvatosystems.t9t.embedded.tests.simple

import com.arvatosystems.t9t.base.ITestConnection
import com.arvatosystems.t9t.base.request.AutonomousTransactionRequest
import com.arvatosystems.t9t.base.request.BatchRequest
import com.arvatosystems.t9t.base.request.LogMessageRequest
import com.arvatosystems.t9t.embedded.connect.InMemoryConnection
import org.junit.BeforeClass
import org.junit.Test

class AutonomousTransactionTest {

    static private ITestConnection dlg

    @BeforeClass
    def public static void createConnection() {
        // use a single connection for all tests (faster)
        dlg = new InMemoryConnection
    }

    @Test
    def public void hellosByAutonomousTransactionTest() {
        val rqInner = new BatchRequest => [
            commands = #[
                new LogMessageRequest("inner thread, BEFORE"),
                new AutonomousTransactionRequest(new LogMessageRequest("second nested autonomous transaction")),
                new LogMessageRequest("inner thread, AFTER")
            ]
            validate
        ]
        val rq = new BatchRequest => [
            commands = #[
                new LogMessageRequest("main thread, BEFORE"),
                new AutonomousTransactionRequest(rqInner),
                new LogMessageRequest("main thread, AFTER")
            ]
            validate
        ]

        dlg.okIO(rq)
    }
}
