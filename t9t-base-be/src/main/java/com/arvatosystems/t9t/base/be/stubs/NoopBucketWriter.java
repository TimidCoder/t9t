package com.arvatosystems.t9t.base.be.stubs;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.event.BucketWriteKey;
import com.arvatosystems.t9t.base.services.IBucketWriter;

import de.jpaw.bonaparte.util.ToStringHelper;
import de.jpaw.dp.Any;
import de.jpaw.dp.Fallback;
import de.jpaw.dp.Singleton;

@Fallback
@Any
@Singleton
public class NoopBucketWriter implements IBucketWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoopBucketWriter.class);

    public NoopBucketWriter() {
        LOGGER.warn("Discarding bucket writer selected - bucket writing commands will be ignored");
    }

    @Override
    public void writeToBuckets(Map<BucketWriteKey, Integer> cmds) {
        LOGGER.debug("No-OP bucket writer: Disarding {}", ToStringHelper.toStringML(cmds));
    }

    @Override
    public void open() {   // NO OP
    }

    @Override
    public void close() {   // NO OP
    }
}
