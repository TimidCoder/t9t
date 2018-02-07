package com.arvatosystems.t9t.exceptions.tests

import com.arvatosystems.t9t.base.ITestConnection
import com.arvatosystems.t9t.embedded.connect.InMemoryConnection
import de.jpaw.annotations.AddLogger
import org.junit.BeforeClass
import org.junit.Test

@AddLogger
class ITShowAllExceptions {

    static private ITestConnection dlg

    @BeforeClass
    def public static void createConnection() {
        // use a single connection for all tests (faster)
        dlg = new InMemoryConnection
    }

    @Test
    def void listExceptions() {
        // first, actively load all exception classes (among others)
        LOGGER.info("Skipping Init")
        // Init.initializeT9t // Init removed because it will be initialized with the inmemoryConnection instead

        // then ask the map for all loaded codes and the corresponding texts
        JustATrickToAccessCodeToDescription.listAllExceptions
    }
}
