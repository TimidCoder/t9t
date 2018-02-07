package com.arvatosystems.t9t.in.be.impl

import com.arvatosystems.t9t.in.services.IInputSession
import de.jpaw.dp.Jdp
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.Map
import java.util.UUID

/** Utility class to test an import. */
class ImportTools {
    /** Imports from a String. */
    def static public importFromString(String source, UUID apiKey, String sourceName, String dataSinkId, Map<String, Object> map) {
        val is = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8))
        importFromStream(is, apiKey, sourceName, dataSinkId, map)
    }

    /** Imports from an InputStream. */
    def static public importFromStream(InputStream is, UUID apiKey, String sourceName, String dataSinkId, Map<String, Object> map) {
        // process a file / input stream, which can be binary
        val inputSession    = Jdp.getRequired(IInputSession)
        inputSession.open(dataSinkId, apiKey, sourceName, map)
        inputSession.process(is)
        inputSession.close
        is.close
    }
}
