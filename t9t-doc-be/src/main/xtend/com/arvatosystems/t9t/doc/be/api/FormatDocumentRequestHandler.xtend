package com.arvatosystems.t9t.doc.be.api;

import com.arvatosystems.t9t.base.services.AbstractRequestHandler
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.doc.DocComponentDTO
import com.arvatosystems.t9t.doc.api.FormatDocumentRequest
import com.arvatosystems.t9t.doc.api.FormatDocumentResponse
import com.arvatosystems.t9t.doc.api.TemplateType
import com.arvatosystems.t9t.doc.services.IDocFormatter
import de.jpaw.bonaparte.pojos.api.media.MediaData
import de.jpaw.dp.Inject
import java.util.HashMap

public class FormatDocumentRequestHandler extends AbstractRequestHandler<FormatDocumentRequest>  {

    @Inject IDocFormatter docFormatter

    override execute(RequestContext ctx, FormatDocumentRequest request) throws Exception {
        val attachments = if (request.binaryAsAttachments) new HashMap<String, MediaData>(16);
        val sharedTenantRef = ctx.tenantMapping.getSharedTenantRef(DocComponentDTO.class$rtti)
        val result = docFormatter.formatDocument(sharedTenantRef, TemplateType.DOCUMENT_ID, request.documentId, request.documentSelector, request.timeZone, request.data, attachments)
        return new FormatDocumentResponse => [
            mediaType       = result.mediaType
            text            = result.text
            it.attachments  = attachments
        ]
    }
}
