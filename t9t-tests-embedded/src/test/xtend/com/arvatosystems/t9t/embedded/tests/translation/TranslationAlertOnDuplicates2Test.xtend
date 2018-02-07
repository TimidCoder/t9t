package com.arvatosystems.t9t.embedded.tests.translation

import com.arvatosystems.t9t.jdp.Init
import com.arvatosystems.t9t.translation.be.TranslationsStack
import de.jpaw.annotations.AddLogger
import org.junit.Ignore
import org.junit.Test

// alternate implementation of the translation duplicate test, without an InMemoryConnection => needs a valid backend database for that reason
@AddLogger
class TranslationAlertOnDuplicates2Test {

    @Ignore
    @Test
    def public void alertOnDuplicateTranslationsTest() {
        Init.initializeT9t();  // initialize
        val numDuplicates = TranslationsStack.numberOfDuplicateTranslations
        if (numDuplicates != 0) {
            LOGGER.error("{} overwritten translation entries exist - check logs for details", numDuplicates)
            throw new Exception(numDuplicates + " duplicate translation entries exist")
        } else {
            LOGGER.info("Everything fine - no duplicate translations found")
        }
    }
}
