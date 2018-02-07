package com.arvatosystems.t9t.doc.be.impl

import com.arvatosystems.t9t.base.T9tException
import com.arvatosystems.t9t.base.types.Recipient
import com.arvatosystems.t9t.doc.api.DocumentSelector
import com.arvatosystems.t9t.doc.services.IDocUnknownDistributor
import de.jpaw.bonaparte.pojos.api.media.MediaData
import de.jpaw.bonaparte.pojos.api.media.MediaXType
import de.jpaw.dp.Fallback
import de.jpaw.dp.Singleton
import java.util.function.Function

// class should be overridden to add more types of Recipients
@Singleton
@Fallback
class DocUnknownDistributor implements IDocUnknownDistributor {
    override transmit(Recipient rcpt, Function<MediaXType, MediaData> data, MediaXType primaryFormat, String documentTemplateId, DocumentSelector documentSelector) {
        throw new T9tException(T9tException.ILLEGAL_REQUEST_PARAMETER, "recipient type " + rcpt.ret$PQON + " not recognized by distribution")
    }
}
