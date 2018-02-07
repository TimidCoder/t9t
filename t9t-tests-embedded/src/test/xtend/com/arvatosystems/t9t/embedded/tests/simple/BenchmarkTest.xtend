package com.arvatosystems.t9t.embedded.tests.simple

import com.arvatosystems.t9t.base.ITestConnection
import com.arvatosystems.t9t.base.request.PingRequest
import com.arvatosystems.t9t.base.request.SimpleBenchmarkRequest
import com.arvatosystems.t9t.embedded.connect.InMemoryConnection
import org.junit.BeforeClass
import org.junit.Test

class BenchmarkTest {

    static private ITestConnection dlg

    @BeforeClass
    def public static void createConnection() {
        // use a single connection for all tests (faster)
        dlg = new InMemoryConnection
    }

    @Test
    def public void benchmarkPingTest() {
        val benchmarkRq = new SimpleBenchmarkRequest => [
            numberOfIterations = 1000
            runAutonomous      = false
            request            = new PingRequest
        ]
        dlg.okIO(benchmarkRq)  // warmup
        dlg.okIO(benchmarkRq)  // the real one
    }
    @Test
    def public void benchmarkPingAutonTest() {
        val benchmarkRq = new SimpleBenchmarkRequest => [
            numberOfIterations = 1000
            runAutonomous      = true
            request            = new PingRequest
        ]
        dlg.okIO(benchmarkRq)  // warmup
        dlg.okIO(benchmarkRq)  // the real one
    }
}
