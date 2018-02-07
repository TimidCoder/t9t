package com.arvatosystems.t9t.msglog.be.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.msglog.MessageDTO;
import com.arvatosystems.t9t.msglog.services.IMsglogPersistenceAccess;

import de.jpaw.dp.Fallback;
import de.jpaw.dp.Singleton;

@Fallback
@Singleton
public class RequestLoggerToDevNull implements IMsglogPersistenceAccess {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLoggerToDevNull.class);
    private int count = 0;

    public int getCount() {
        return count;
    }

    @Override
    public void open() {
        LOGGER.warn("Msglog to /dev/null - do not use in production");
    }

    @Override
    public void write(List<MessageDTO> entries) {
        ++count;
    }

    @Override
    public void close() {
    }
}
