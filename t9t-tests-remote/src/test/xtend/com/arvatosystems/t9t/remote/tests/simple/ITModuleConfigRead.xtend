package com.arvatosystems.t9t.remote.tests.simple

import com.arvatosystems.t9t.auth.request.AuthModuleCfgSearchRequest
import com.arvatosystems.t9t.base.ITestConnection
import com.arvatosystems.t9t.base.search.ReadAllResponse
import com.arvatosystems.t9t.doc.request.DocModuleCfgSearchRequest
import com.arvatosystems.t9t.email.request.EmailModuleCfgSearchRequest
import com.arvatosystems.t9t.remote.connect.Connection
import com.arvatosystems.t9t.solr.request.SolrModuleCfgSearchRequest
import de.jpaw.bonaparte.util.ToStringHelper
import org.junit.BeforeClass
import org.junit.Test

class ITModuleConfigRead {
    static private ITestConnection dlg

    @BeforeClass
    def public static void createConnection() {
        // use a single connection for all tests (faster)
        dlg = new Connection
    }

    // email module config search request
    @Test
    def public void readEmailModuleConfigTest() {
        val rq = new EmailModuleCfgSearchRequest
        val resp = dlg.typeIO(rq, ReadAllResponse)
        println('''EmailCfg result is «ToStringHelper.toStringML(resp.dataList)»''')
    }

    @Test
    def public void readEmailModuleConfigWithLimitTest() {
        val rq = new EmailModuleCfgSearchRequest => [ limit = 26 ]
        val resp = dlg.typeIO(rq, ReadAllResponse)
        println('''EmailCfg result is «ToStringHelper.toStringML(resp.dataList)»''')
    }

    // doc module config search request
    @Test
    def public void readDocModuleConfigTest() {
        val rq = new DocModuleCfgSearchRequest
        val resp = dlg.typeIO(rq, ReadAllResponse)
        println('''DocModule result is «ToStringHelper.toStringML(resp.dataList)»''')
    }

    // auth module config search request
    @Test
    def public void readAuthModuleConfigTest() {
        val rq = new AuthModuleCfgSearchRequest
        val resp = dlg.typeIO(rq, ReadAllResponse)
        println('''Auth Module Cfg result is «ToStringHelper.toStringML(resp.dataList)»''')
    }

    // SOLR module config search request
    @Test
    def public void readSolrModuleConfigTest() {
        val rq = new SolrModuleCfgSearchRequest
        val resp = dlg.typeIO(rq, ReadAllResponse)
        println('''SOLR module config result is «ToStringHelper.toStringML(resp.dataList)»''')
    }
}
