package com.arvatosystems.t9t.remote.tests.simple

import com.arvatosystems.t9t.base.request.RetrieveComponentInfoRequest
import com.arvatosystems.t9t.base.request.RetrieveComponentInfoResponse
import com.arvatosystems.t9t.remote.connect.Connection
import org.junit.Test
import de.jpaw.bonaparte.util.ToStringHelper

class ITComponentInfo {
    @Test
    def public void infoTest() {
        val dlg = new Connection

        val resp = dlg.typeIO(new RetrieveComponentInfoRequest, RetrieveComponentInfoResponse)
        println(ToStringHelper.toStringML(resp))
    }
}
