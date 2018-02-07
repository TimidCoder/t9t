package com.arvatosystems.t9t.auth.be.tests

import org.junit.Test

class GetResources {

    @Test
    def public void getIcon() {

        val keystore = GetResources.getResource("/t9tkeystore.jceks")
        if (keystore === null)
            throw new Exception("keystore not found")
    }
}
