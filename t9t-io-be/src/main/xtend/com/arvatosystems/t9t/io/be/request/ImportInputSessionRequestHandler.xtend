package com.arvatosystems.t9t.io.be.request

import com.arvatosystems.t9t.base.T9tException
import com.arvatosystems.t9t.base.services.AbstractRequestHandler
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.io.request.AbstractImportData
import com.arvatosystems.t9t.io.request.ImportFromFile
import com.arvatosystems.t9t.io.request.ImportFromRaw
import com.arvatosystems.t9t.io.request.ImportFromString
import com.arvatosystems.t9t.io.request.ImportInputSessionRequest
import de.jpaw.annotations.AddLogger
import de.jpaw.util.ApplicationException
import java.io.ByteArrayInputStream
import java.io.FileInputStream
import java.nio.charset.StandardCharsets
import com.arvatosystems.t9t.in.be.impl.ImportTools

@AddLogger
class ImportInputSessionRequestHandler extends AbstractRequestHandler<ImportInputSessionRequest> {

    override execute(RequestContext nullCtx, ImportInputSessionRequest rq) {
        ImportTools.importFromStream(getInput(rq.data), rq.apiKey, rq.sourceName, rq.dataSinkId, rq.additionalParameters)
        return ok
    }


    // specific input types: return all of them as stream
    def dispatch getInput(ImportFromString ifs) {
        return new ByteArrayInputStream(ifs.text.getBytes(StandardCharsets.UTF_8))
    }
    def dispatch getInput(ImportFromRaw ifr) {
        return ifr.data.asByteArrayInputStream
    }
    def dispatch getInput(ImportFromFile iff) {
        if (iff.isResource)
            return iff.class.getResourceAsStream(iff.pathname)
        else
            return new FileInputStream(iff.pathname)
    }
    def dispatch getInput(AbstractImportData aid) {
        throw new ApplicationException(T9tException.NOT_YET_IMPLEMENTED, "No valid import source for " + aid.class.canonicalName)
    }
}
