package com.arvatosystems.t9t.remote.tests.simple

import com.arvatosystems.t9t.auth.request.SessionSearchRequest
import com.arvatosystems.t9t.base.search.ReadAllResponse
import com.arvatosystems.t9t.remote.connect.Connection
import de.jpaw.bonaparte.pojos.api.SortColumn
import de.jpaw.bonaparte.pojos.apiw.DataWithTrackingW
import org.junit.Test

class ITSessions {
    @Test
    def public void querySessionsTest() {
        val dlg = new Connection

        val results = dlg.typeIO((new SessionSearchRequest => [
            sortColumns = # [
                new SortColumn => [
                    descending = true
                    fieldName = "cTimestamp"
                ]
            ]
            limit = 20
        ]), ReadAllResponse).dataList
        for (dwt: results)
            println('''«(dwt as DataWithTrackingW).data»''')
    }
}
