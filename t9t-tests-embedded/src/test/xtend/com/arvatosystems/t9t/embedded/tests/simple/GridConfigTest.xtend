package com.arvatosystems.t9t.embedded.tests.simple

import com.arvatosystems.t9t.auth.request.AuthModuleCfgSearchRequest
import com.arvatosystems.t9t.base.ITestConnection
import com.arvatosystems.t9t.base.search.ReadAllResponse
import com.arvatosystems.t9t.doc.request.DocModuleCfgSearchRequest
import com.arvatosystems.t9t.email.request.EmailModuleCfgSearchRequest
import com.arvatosystems.t9t.embedded.connect.InMemoryConnection
import com.arvatosystems.t9t.solr.request.SolrModuleCfgSearchRequest
import com.arvatosystems.t9t.uiprefs.request.GridConfigRequest
import de.jpaw.bonaparte.util.ToStringHelper
import org.junit.BeforeClass
import org.junit.Test
import org.junit.Assert

class GridConfigTest {
    static private ITestConnection dlg

    @BeforeClass
    def public static void createConnection() {
        // use a single connection for all tests (faster)
        dlg = new InMemoryConnection
    }

    @Test
    def public void readEmailModuleConfigWithLimitTest() {
        val rq = new EmailModuleCfgSearchRequest => [ limit = 26 ]
        val resp = dlg.typeIO(rq, ReadAllResponse)
        println('''EmailCfg result is «ToStringHelper.toStringML(resp.dataList)»''')
    }

    // email module config search request
    @Test
    def public void readEmailModuleConfigTest() {
        val rq = new EmailModuleCfgSearchRequest
        val resp = dlg.typeIO(rq, ReadAllResponse)
        Assert.assertEquals(0, resp.dataList.size)
    }

    // doc module config search request
    @Test
    def public void readDocModuleConfigTest() {
        val rq = new DocModuleCfgSearchRequest
        val resp = dlg.typeIO(rq, ReadAllResponse)
        Assert.assertEquals(0, resp.dataList.size)
    }

    // auth module config search request
    @Test
    def public void readAuthModuleConfigTest() {
        val rq = new AuthModuleCfgSearchRequest
        val resp = dlg.typeIO(rq, ReadAllResponse)
        Assert.assertEquals(0, resp.dataList.size)
    }

    // SOLR module config search request
    @Test
    def public void readSolrModuleConfigTest() {
        val rq = new SolrModuleCfgSearchRequest
        val resp = dlg.typeIO(rq, ReadAllResponse)
        Assert.assertEquals(0, resp.dataList.size)
    }

    @Test
    def public void configEnTest() {
        val rq = new GridConfigRequest => [
            gridId                    = "docComponent"
            translateInvisibleHeaders = true
            validate
        ]

        println(ToStringHelper.toStringML(dlg.okIO(rq)))
    }

    @Test
    def public void configDeTest() {
        val rq = new GridConfigRequest => [
            gridId                    = "docConfig"
            translateInvisibleHeaders = true
            overrideLanguage          = "de"
            validate
        ]

        println(ToStringHelper.toStringML(dlg.okIO(rq)))
    }
}
