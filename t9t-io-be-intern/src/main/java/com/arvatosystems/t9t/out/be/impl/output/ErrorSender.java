package com.arvatosystems.t9t.out.be.impl.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.io.AsyncChannelDTO;
import com.arvatosystems.t9t.io.AsyncHttpResponse;
import com.arvatosystems.t9t.io.AsyncQueueDTO;
import com.arvatosystems.t9t.out.services.IAsyncSender;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.dp.Dependent;
import de.jpaw.dp.Named;

/**
 * The ErrorSender has been created for testing purposes. It always returns a "server error".
 */
@Dependent
@Named("ERROR")
public class ErrorSender implements IAsyncSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorSender.class);

    @Override
    public void init(AsyncQueueDTO queue) {
        LOGGER.info("Creating IAsyncSender ERROR for queue {}", queue.getAsyncQueueId());
    }

    @Override
    public AsyncHttpResponse send(AsyncChannelDTO channel, BonaPortable payload, int timeout, Long messageObjectRef) {
        LOGGER.debug("Returning error for channel {}, object {}", channel.getAsyncChannelId(), payload.ret$PQON());
        AsyncHttpResponse resp = new AsyncHttpResponse();
        resp.setHttpReturnCode(500);
        resp.setHttpStatusMessage("Error");
        return resp;
    }
}
