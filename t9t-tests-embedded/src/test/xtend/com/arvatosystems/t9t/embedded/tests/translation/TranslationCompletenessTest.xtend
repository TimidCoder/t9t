package com.arvatosystems.t9t.embedded.tests.translation

import com.arvatosystems.t9t.base.ITestConnection
import com.arvatosystems.t9t.embedded.connect.InMemoryConnection
import com.arvatosystems.t9t.misc.translation.TranslationCompleteness
import de.jpaw.annotations.AddLogger
import org.junit.BeforeClass
import org.junit.Test

@AddLogger
class TranslationCompletenessTest {
    static private ITestConnection dlg

    @BeforeClass
    def public static void createConnection() {
        // use a single connection for all tests (faster)
        dlg = new InMemoryConnection
    }

    @Test
    def public void gridHeadersEnTest() {
        TranslationCompleteness.gridHeadersTestOneLanguage(dlg, "en")
    }

    @Test
    def public void gridHeadersDeTest() {
        TranslationCompleteness.gridHeadersTestOneLanguage(dlg, "de")
    }
}
