package com.arvatosystems.t9t.base.services;

import java.util.function.Consumer;

import de.jpaw.bonaparte.core.BonaPortable;

/** To be injected for the cache invalidation registry.
 * Implementations are normally singletons. */
public interface ICacheInvalidationRegistry {
    public void registerInvalidator(String dto, Consumer<BonaPortable> invalidator);
    public Consumer<BonaPortable> getInvalidator(String dto);
}
