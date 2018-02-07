package com.arvatosystems.t9t.base.be.stubs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.api.ServiceRequest;
import com.arvatosystems.t9t.base.event.EventData;
import com.arvatosystems.t9t.base.services.IAsyncRequestProcessor;
import com.arvatosystems.t9t.base.services.IEventHandler;

import de.jpaw.dp.Any;
import de.jpaw.dp.Fallback;
import de.jpaw.dp.Singleton;

@Fallback
@Any
@Singleton
public class NoAsyncProcessor implements IAsyncRequestProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoAsyncProcessor.class);

    public NoAsyncProcessor() {
        LOGGER.warn("NoAsyncProcessor implementation selected - all async commands will be discarded");
    }

    @Override
    public void submitTask(ServiceRequest request) {
        LOGGER.debug("async request {} discarded", request.getRequestParameters().ret$PQON());
    }

    @Override
    public void send(EventData data) {
        LOGGER.debug("async event {} discarded", data.getData().ret$PQON());
    }

    @Override
    public void publish(EventData data) {
        LOGGER.debug("async event {} discarded", data.getData().ret$PQON());
    }

    @Override
    public void registerSubscriber(String eventID, IEventHandler subscriber) {
        LOGGER.debug("subscriber (not) registered: {} for {}", subscriber.getClass().getCanonicalName(), eventID);
    }
}
