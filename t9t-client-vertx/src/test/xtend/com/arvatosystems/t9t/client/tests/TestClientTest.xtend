package com.arvatosystems.t9t.client.tests

import com.arvatosystems.t9t.base.request.PauseRequest
import com.arvatosystems.t9t.client.TestSession
import io.vertx.ext.unit.TestContext
import io.vertx.ext.unit.junit.VertxUnitRunner
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(VertxUnitRunner)
public class TestClientTest extends TestSession {

    @Before
    override public void setUp(TestContext context) {
        super.setUp(context)
    }

    @After
    override public void tearDown(TestContext context) {
        super.tearDown(context)
    }

    @Test
    def public void testGetFavicon(TestContext context) {
        val async = context.async
        exec(new PauseRequest)
        async.complete
    }
}
