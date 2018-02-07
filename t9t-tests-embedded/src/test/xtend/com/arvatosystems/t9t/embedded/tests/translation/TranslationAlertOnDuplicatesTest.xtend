package com.arvatosystems.t9t.embedded.tests.translation

import com.arvatosystems.t9t.embedded.connect.InMemoryConnection
import com.arvatosystems.t9t.translation.be.TranslationsStack
import de.jpaw.annotations.AddLogger
import org.junit.Test

@AddLogger
class TranslationAlertOnDuplicatesTest {

    @Test
    def public void alertOnDuplicateTranslationsTest() {
        new InMemoryConnection  // initialize
        val numDuplicates = TranslationsStack.numberOfDuplicateTranslations
        if (numDuplicates != 0) {
            LOGGER.error("{} overwritten translation entries exist - check logs for details", numDuplicates)
            throw new Exception(numDuplicates + " duplicate translation entries exist")
        } else {
            LOGGER.info("Everything fine - no duplicate translations found")
        }
    }
}
