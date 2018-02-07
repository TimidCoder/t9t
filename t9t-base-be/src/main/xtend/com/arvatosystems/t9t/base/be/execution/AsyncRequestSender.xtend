package com.arvatosystems.t9t.base.be.execution

import com.arvatosystems.t9t.base.api.RequestParameters
import com.arvatosystems.t9t.base.api.ServiceRequest
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.base.types.AuthenticationJwt
import com.arvatosystems.t9t.base.types.XTargetChannelType
import com.arvatosystems.t9t.server.services.IAsyncRequestSender
import com.arvatosystems.t9t.server.services.IEvent
import de.jpaw.annotations.AddLogger
import de.jpaw.bonaparte.api.media.MediaTypes
import de.jpaw.bonaparte.core.BonaPortable
import de.jpaw.bonaparte.core.BonaparteJsonEscaper
import de.jpaw.bonaparte.core.CompactByteArrayComposer
import de.jpaw.bonaparte.core.StaticMeta
import de.jpaw.bonaparte.core.StringBuilderComposer
import de.jpaw.bonaparte.pojos.api.media.MediaData
import de.jpaw.bonaparte.pojos.api.media.MediaType
import de.jpaw.bonaparte.pojos.api.media.MediaXType
import de.jpaw.dp.Inject
import de.jpaw.dp.Provider
import de.jpaw.dp.Singleton

// send a request to an external arbitrary address (not the vert.x eventBus)
@AddLogger
@Singleton
class AsyncRequestSender implements IAsyncRequestSender {
    private static final MediaXType DEFAULT_MEDIA_TYPE = MediaTypes.MEDIA_XTYPE_JSON;

    @Inject IEvent event
    @Inject Provider<RequestContext> ctx

    override asyncRequest(XTargetChannelType channel, String address, BonaPortable rq, MediaXType serializationFormat) {
        val sFormat = serializationFormat?.baseEnum
        if (sFormat === null || sFormat === MediaType.JSON) {
            val data = new MediaData => [
                mediaType   = DEFAULT_MEDIA_TYPE
                text        = BonaparteJsonEscaper.asJson(rq)
            ]
            event.asyncEvent(channel, address, data)
            return
        }
        if (sFormat === MediaType.BONAPARTE) {
            val data = new MediaData => [
                mediaType   = serializationFormat
                text        = StringBuilderComposer.marshal(StaticMeta.OUTER_BONAPORTABLE, rq)
            ]
            event.asyncEvent(channel, address, data)
            return
        }
        if (sFormat === MediaType.COMPACT_BONAPARTE) {
            val data = new MediaData => [
                mediaType   = serializationFormat
                rawData     = CompactByteArrayComposer.marshalAsByteArray(StaticMeta.OUTER_BONAPORTABLE, rq)
            ]
            event.asyncEvent(channel, address, data)
            return
        }
        throw new UnsupportedOperationException("Unsupported media type for IAsyncRequestSender")
    }

    override asyncServiceRequest(XTargetChannelType channel, String address, RequestParameters rq, MediaXType serializationFormat) {
        val currentContext = ctx.get
        LOGGER.debug("AsyncServiceRequestSender called for channel {}, address {}, request type {}, format {}, user {}, tenant {}",
            channel.token, address, rq.ret$PQON, serializationFormat ?: "(default)", currentContext.userId, currentContext.tenantId
        )
        val srq = new ServiceRequest(null, rq, new AuthenticationJwt(currentContext.internalHeaderParameters.encodedJwt));
        asyncRequest(channel, address, srq, serializationFormat)
    }
}
