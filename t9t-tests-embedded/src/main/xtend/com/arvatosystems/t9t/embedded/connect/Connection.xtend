package com.arvatosystems.t9t.embedded.connect

import com.arvatosystems.t9t.cfg.be.ConfigProvider
import com.arvatosystems.t9t.jdp.Init
import de.jpaw.annotations.AddLogger
import java.util.UUID

@AddLogger
class Connection extends AbstractConnection {

    private static final Object DUMMY_INITIALIZER   = {
        val cfgFile = System.getProperty("T9T_TESTS_EMBEDDED_CFGFILE")
        ConfigProvider.readConfiguration(cfgFile);        // update a possible new location of the config file before we run the startup process
        Init.initializeT9t
        return null
    }

    def protected static getInitialPassword() {
        return System.getProperty("t9t.password") ?: System.getenv("PASSWORD") ?: INITIAL_PASSWORD
    }

    new() {
        auth(INITIAL_USER_ID, getInitialPassword)
    }

    new(UUID apiKey) {
        auth(apiKey)
    }
}
