package com.arvatosystems.t9t.embedded.tests.simple

import com.arvatosystems.t9t.base.ITestConnection
import com.arvatosystems.t9t.base.request.LogMessageRequest
import com.arvatosystems.t9t.core.CannedRequestDTO
import com.arvatosystems.t9t.core.CannedRequestKey
import com.arvatosystems.t9t.core.request.ExecuteCannedRequest
import com.arvatosystems.t9t.embedded.connect.InMemoryConnection
import org.junit.BeforeClass
import org.junit.Test

import static extension com.arvatosystems.t9t.misc.extensions.MiscExtensions.*

class CannedRequestTest {

    static private ITestConnection dlg

    @BeforeClass
    def public static void createConnection() {
        // use a single connection for all tests (faster)
        dlg = new InMemoryConnection
    }

    @Test
    def public void helloByDtoWithParametersTest() {
        val dto = new CannedRequestDTO => [
            requestId               = "helloTest1"
            name                    = "helloTest1"
            jobParameters           = #{ "message" -> "hello, first world" }
            jobRequestObjectName    = "t9t.base.request.LogMessageRequest"
            validate
        ]

        dlg.okIO(new ExecuteCannedRequest(dto))
    }

    @Test
    def public void helloByDtoWithObjectTest() {
        val dto = new CannedRequestDTO => [
            requestId               = "helloTest2"
            name                    = "helloTest2"
            request                 = new LogMessageRequest("hello, second world")
        ]

        dlg.okIO(new ExecuteCannedRequest(dto))
    }


    @Test
    def public void helloWithPersistenceTest() {
        // create a canned request and persist it
        new CannedRequestDTO => [
            requestId               = "helloTestP"
            name                    = "helloTestP"
            jobParameters           = #{ "message" -> "hello, persisted world" }
            jobRequestObjectName    = "t9t.base.request.LogMessageRequest"
            merge(dlg)
        ]
        dlg.okIO(new ExecuteCannedRequest(new CannedRequestKey("helloTestP")))
    }
}
