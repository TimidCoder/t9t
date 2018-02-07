package com.arvatosystems.t9t.embedded.tests.simple

import com.arvatosystems.t9t.base.ITestConnection
import com.arvatosystems.t9t.base.request.ExecuteJsonMapRequest
import com.arvatosystems.t9t.base.request.ExecuteJsonRequest
import com.arvatosystems.t9t.embedded.connect.InMemoryConnection
import org.junit.BeforeClass
import org.junit.Test

class JsonRequestTest {

    static private ITestConnection dlg

    @BeforeClass
    def public static void createConnection() {
        // use a single connection for all tests (faster)
        dlg = new InMemoryConnection
    }

    @Test
    def public void helloByJsonStringTest() {
        val rq = new ExecuteJsonRequest => [
            request = '{ "@PQON": "t9t.base.request.LogMessageRequest", "message": "Hello from JSON" }'
            validate
        ]
        dlg.okIO(rq)
    }

    @Test
    def public void helloByJsonMapTest() {
        val rq = new ExecuteJsonMapRequest => [
            request = #{
                "@PQON" -> "t9t.base.request.LogMessageRequest",
                "message" -> "Hello from JSON"
            }
            validate
        ]
        dlg.okIO(rq)
    }
}
