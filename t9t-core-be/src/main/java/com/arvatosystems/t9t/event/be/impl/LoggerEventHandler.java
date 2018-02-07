package com.arvatosystems.t9t.event.be.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.event.EventParameters;
import com.arvatosystems.t9t.base.services.IEventHandler;
import com.arvatosystems.t9t.base.services.RequestContext;

import de.jpaw.bonaparte.util.ToStringHelper;
import de.jpaw.dp.Named;
import de.jpaw.dp.Singleton;

@Singleton
@Named("logger")
public class LoggerEventHandler implements IEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerEventHandler.class);

    @Override
    public int execute(RequestContext ctx, EventParameters eventData) {
        LOGGER.info("Event data is {}", ToStringHelper.toStringML(eventData));
        return 0;
    }
}
