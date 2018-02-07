package com.arvatosystems.t9t.embedded.tests.translation

import com.arvatosystems.t9t.base.ITestConnection
import com.arvatosystems.t9t.base.T9tConstants
import com.arvatosystems.t9t.embedded.connect.InMemoryConnection
import com.arvatosystems.t9t.io.CommunicationTargetChannelType
import com.arvatosystems.t9t.translation.services.ITranslationProvider
import de.jpaw.dp.Jdp
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test

class EnumDirectTranslationTest {
    static private ITestConnection dlg

    @BeforeClass
    def public static void createConnection() {
        // use a single connection for all tests (faster)
        dlg = new InMemoryConnection
    }

    @Test
    def enumXlateTest() {
        val translator = Jdp.getRequired(ITranslationProvider)

        val xlates = translator.getEnumTranslation(T9tConstants.GLOBAL_TENANT_ID, CommunicationTargetChannelType.enum$MetaData.name, "de", false)
        for (e: xlates.entrySet)
            println('''«e.key» maps to «e.value»''')
        Assert.assertEquals("Datei", xlates.get("FILE"))
    }
}
