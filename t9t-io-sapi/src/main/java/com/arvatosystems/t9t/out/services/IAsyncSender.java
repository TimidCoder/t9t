package com.arvatosystems.t9t.out.services;

import com.arvatosystems.t9t.io.AsyncChannelDTO;
import com.arvatosystems.t9t.io.AsyncHttpResponse;
import com.arvatosystems.t9t.io.AsyncQueueDTO;

import de.jpaw.bonaparte.core.BonaPortable;

/**
 * Describes an implementation of a REST POST or SOAP WS endpoint.
 * The implementations are @Dependent and are constructed once per queue.
 */
public interface IAsyncSender {
    /** Called once after construction. */
    void init(AsyncQueueDTO queue);

    /**
     * Called per request, attempts to send the payload.
     * The messageObjectRef may be used by implementations to generate unique IDs, and for logging / debugging purposes.
     * It represents the key of the AsyncMessageEntity.
     * */
    AsyncHttpResponse send(AsyncChannelDTO channel, BonaPortable payload, int timeout, Long messageObjectRef) throws Exception;
}
