package com.arvatosystems.t9t.remote.tests.simple

import com.arvatosystems.t9t.io.request.FileDownloadRequest
import com.arvatosystems.t9t.io.request.FileDownloadResponse
import com.arvatosystems.t9t.remote.connect.Connection
import com.arvatosystems.t9t.remote.connect.ConnectionTypes
import org.junit.Test

class ITDownload {
    @Test
    def public void downloadTest() {
        val dlg = new Connection(true, ConnectionTypes.JSON)

        dlg.typeIO(new FileDownloadRequest => [
            sinkRef = 2001200017L
        ], FileDownloadResponse)
    }
}
