package com.arvatosystems.t9t.embedded.tests.simple

import com.arvatosystems.t9t.base.T9tConstants
import com.arvatosystems.t9t.msglog.MessageDTO
import org.joda.time.Instant
import org.junit.Test

// errors have been reported with language codes according to BCP47

class TestLanguageCode {

    @Test
    def testLanguages() {
        val msglog = new MessageDTO => [  // create an object which contains a language code with pattern
            sessionRef              = 1L
            tenantRef               = T9tConstants.GLOBAL_TENANT_REF42
            userId                  = "john"
            executionStartedAt      = new Instant
            requestParameterPqon    = "dummy.PingRequest"
            validate
        ]
        #[ "en", "de", "en_GB", "en-US" ].forEach [
            println("Testing language code " + it)
            msglog.languageCode = it
            msglog.validate
        ]
    }
}
