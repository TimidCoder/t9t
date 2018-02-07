package com.arvatosystems.t9t.base.be.request;

import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.request.PauseRequest;
import com.arvatosystems.t9t.base.request.PauseResponse;
import com.arvatosystems.t9t.base.services.AbstractReadOnlyRequestHandler;


/**
 * The default implementation responsible for handling all incoming requests of type {@link PauseRequest}.
 */
public class PauseRequestHandler extends AbstractReadOnlyRequestHandler<PauseRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PauseRequestHandler.class);

    @Override
    public PauseResponse execute(PauseRequest pingRequest) {
//        LOGGER.info("Ping request handler called for {}", pingRequest);

        PauseResponse response = new PauseResponse();
        response.setReturnCode(0);
        response.setPingId(pingRequest.getPingId());
        response.setWhenExecuted(new Instant());

        Integer delayInMillis = pingRequest.getDelayInMillis();
        if ((delayInMillis != null) && (delayInMillis.intValue() > 0)) {
            try {
                Thread.sleep(delayInMillis);
            } catch (InterruptedException e) {
                LOGGER.warn("A PauseRequest with delay {} milliseconds has been interrupted and sent the response earlier.", delayInMillis);
            }
        }

        response.setWhenFinished(new Instant());
        return response;
    }
}
