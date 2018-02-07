package com.arvatosystems.t9t.base.be.eventhandler;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.event.EventParameters;
import com.arvatosystems.t9t.base.event.InvalidateCacheEvent;
import com.arvatosystems.t9t.base.services.ICacheInvalidationRegistry;
import com.arvatosystems.t9t.base.services.IEventHandler;
import com.arvatosystems.t9t.base.services.RequestContext;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.dp.Jdp;
import de.jpaw.dp.Named;
import de.jpaw.dp.Singleton;

@Singleton
@Named("cacheInvalidation")
public class CacheInvalidationEventHandler implements IEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheInvalidationEventHandler.class);
    protected final ICacheInvalidationRegistry registry = Jdp.getRequired(ICacheInvalidationRegistry.class);

    @Override
    public int execute(RequestContext ctx, EventParameters eventData) {
        if (eventData instanceof InvalidateCacheEvent) {
            // OK, query registry and invoke invalidator, if any exists
            InvalidateCacheEvent ice = (InvalidateCacheEvent) eventData;
            Consumer<BonaPortable> invalidator = registry.getInvalidator(ice.getPqon());
            if (invalidator != null) {
                LOGGER.debug("Performing cache invalidation for {} with key {}", ice.getPqon(), ice.getKey());
                invalidator.accept(ice.getKey());
            } else {
                LOGGER.debug("Ignoring cache invalidation event for {} - no invalidator registered", ice.getPqon());
            }
        } else {
            LOGGER.warn("Event data is of type {}, expected InvalidateCacheEvent", eventData.getClass().getCanonicalName());
        }
        return 0;
    }
}
