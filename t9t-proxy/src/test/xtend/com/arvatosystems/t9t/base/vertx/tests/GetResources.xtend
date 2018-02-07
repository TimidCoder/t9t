package com.arvatosystems.t9t.base.vertx.tests

import org.junit.Test

class GetResources {

    @Test
    def public void getIcon() {
        val icon = GetResources.getResource("/web/favicon.ico")

        if (icon === null)
            throw new Exception("not found")
    }
}
