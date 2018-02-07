package com.arvatosystems.t9t.remote.tests.simple

import com.arvatosystems.t9t.remote.connect.Connection
import com.arvatosystems.t9t.uiprefs.request.DumpUntranslatedDefaultsRequest
import com.arvatosystems.t9t.uiprefs.request.DumpUntranslatedHeadersRequest
import org.junit.Test

class ITDumpTranslations {
    @Test
    def public void dumpTranslationHeadersTest() {
        val dlg = new Connection

        dlg.okIO(new DumpUntranslatedHeadersRequest)
    }
    @Test
    def public void dumpTranslationDefaultsTest() {
        val dlg = new Connection

        dlg.okIO(new DumpUntranslatedDefaultsRequest)
    }
}
