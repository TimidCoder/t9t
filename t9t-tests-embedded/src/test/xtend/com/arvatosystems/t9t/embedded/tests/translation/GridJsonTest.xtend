package com.arvatosystems.t9t.embedded.tests.translation

import com.arvatosystems.t9t.base.ITestConnection
import com.arvatosystems.t9t.embedded.connect.InMemoryConnection
import com.arvatosystems.t9t.uiprefs.request.GridConfigRequest
import com.arvatosystems.t9t.uiprefs.request.GridConfigResponse
import de.jpaw.bonaparte.core.JsonComposer
import de.jpaw.bonaparte.util.ToStringHelper
import org.junit.BeforeClass
import org.junit.Test

class GridJsonTest {

    static private ITestConnection dlg

    @BeforeClass
    def public static void createConnection() {
        // use a single connection for all tests (faster)
        dlg = new InMemoryConnection
    }

    @Test
    def public void gridHeaderJsonTest() {
        val rq = new GridConfigRequest => [
            gridId                      = "sliceTracking";
            selection                   = 0;
            translateInvisibleHeaders   = false
            noFallbackLanguages         = true
            overrideLanguage            = "en"
            validate
        ]
        val gridCfg = dlg.typeIO(rq, GridConfigResponse)

        println(ToStringHelper.toStringML(gridCfg.headers))
        println(JsonComposer.toJsonString(gridCfg))
    }
}
