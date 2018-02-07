package com.arvatosystems.t9t.doc.be.api

import com.arvatosystems.t9t.base.api.ServiceResponse
import com.arvatosystems.t9t.base.services.AbstractRequestHandler
import com.arvatosystems.t9t.base.services.IExecutor
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.doc.DocConfigDTO
import com.arvatosystems.t9t.doc.DocEmailReceiverDTO
import com.arvatosystems.t9t.doc.api.SendEditedEmailRequest
import com.arvatosystems.t9t.doc.api.SendEditedEmailResponse
import com.arvatosystems.t9t.doc.recipients.RecipientArchive
import com.arvatosystems.t9t.doc.services.IDocArchiveDistributor
import com.arvatosystems.t9t.doc.services.IDocConverter
import com.arvatosystems.t9t.doc.services.IDocModuleCfgDtoResolver
import com.arvatosystems.t9t.doc.services.IDocPersistenceAccess
import com.arvatosystems.t9t.email.api.EmailMessage
import com.arvatosystems.t9t.email.api.RecipientEmail
import com.arvatosystems.t9t.email.api.SendEmailRequest
import de.jpaw.annotations.AddLogger
import de.jpaw.bonaparte.pojos.api.media.MediaData
import de.jpaw.bonaparte.pojos.api.media.MediaXType
import de.jpaw.dp.Inject
import de.jpaw.util.ApplicationException
import java.util.HashMap
import java.util.function.Function

import static extension de.jpaw.dp.Jdp.*

@AddLogger
class SendEditedEmailRequestHandler extends AbstractRequestHandler<SendEditedEmailRequest> {

    private static final DocEmailReceiverDTO BLANK_EMAIL_SETTINGS = new DocEmailReceiverDTO()

    @Inject IDocPersistenceAccess persistenceAccess
    @Inject IDocModuleCfgDtoResolver moduleConfigResolver
    @Inject IDocArchiveDistributor docArchiveDistributor
    @Inject IExecutor executor

    override SendEditedEmailResponse execute(RequestContext ctx, SendEditedEmailRequest request) {
        val moduleCfg = moduleConfigResolver.moduleConfiguration
        val DocConfigDTO docConfigDto = effectiveDocConfig(request.documentId)

        if (docConfigDto === null) {
            LOGGER.error("Document Config for documentID {} is not being setup.", request.documentId)
            new SendEditedEmailResponse => [
                errorMessage = "Document Config for documentID {} is not being setup."
            ]
        }

        val docEmailReceiverDTO = (if (docConfigDto.emailConfigPerSelector) {
            persistenceAccess.getDocEmailCfgDTO(moduleCfg, request.documentId,
            request.documentSelector)?.emailSettings
        } else {
            docConfigDto.emailSettings
        }) ?: BLANK_EMAIL_SETTINGS

        val recipientEmail = new RecipientEmail => [
            from = docEmailReceiverDTO?.defaultFrom
            to = #[request.to]
        ]

        val sendEmailRequest = new SendEmailRequest => [
            email = new EmailMessage => [
                mailBody = request.mailMediaData
                recipient = recipientEmail
                attachments = #[]
                cids = new HashMap
            ]
        ]

        val recipientArchive = new RecipientArchive => [
            dataSinkId = "CSEmailOut"
            outputSessionParameters = new HashMap
        ]

        val please = new HashMap<MediaXType, MediaData>(8);
        please.put(request.mailMediaData.mediaType, request.mailMediaData)

        val Function<MediaXType, MediaData> toFormatConverter = [
            please.get(it) ?: IDocConverter.getRequired(name)?.convert(request.mailMediaData) => [ away | please.put(it, away) ]
        ]

        val archiveResult = docArchiveDistributor.transmit(recipientArchive, toFormatConverter, request.mailMediaData.mediaType,
            request.documentId, request.documentSelector)

        val res = executor.executeSynchronousAndCheckResult(ctx, sendEmailRequest, ServiceResponse)
        val rs = new SendEditedEmailResponse => [
            returnCode = res.returnCode
            archiveSinkRefs = archiveResult.sinkRef
        ]

        if (res.returnCode != ApplicationException.SUCCESS) {
            rs.errorDetails = res.errorDetails
            rs.errorMessage = res.errorMessage
        }

        return rs
    }

    def protected effectiveDocConfig(String templateId) {
        var String nextTemplateId = templateId
        for (;;) {
            val docConfigDto = persistenceAccess.getDocConfigDTO(nextTemplateId)
            if (docConfigDto.mappedId === null)
                return null     // mapped to "no op" / skip this document
            if (docConfigDto.documentId == docConfigDto.mappedId || Boolean.TRUE != docConfigDto.followMappedId)
                return docConfigDto  // found effective one, no further mapping
            nextTemplateId = docConfigDto.mappedId
        }
    }

}
