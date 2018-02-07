package com.arvatosystems.t9t.base.services.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LazyInjection<V> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LazyInjection.class);
    final Callable<V> initializer;
    final AtomicReference<V> value;

    /** The Callable must provide idempotent values. */
    public LazyInjection(Callable<V> initializer) {
        this.initializer = initializer;
        this.value = new AtomicReference<>();
    }

    public V get() throws Exception {
        V val = value.get();
        if (val == null) {
            // perform lazy initialization now (non-synchronized because we assume (& check) idempotency)
            val = initializer.call();
            V oldVal = value.getAndSet(val);
            if (oldVal == null) {
                LOGGER.debug("Lazy initialization of {} performed", val.getClass().getCanonicalName());
            } else if (oldVal == val) {
                LOGGER.debug("Lazy initialization of {} performed DUPLICATE (safe)", val.getClass().getCanonicalName());
            } else {
                LOGGER.error("DUPLICATE lazy initialization of {} performed, factory is NOT IDEMPOTENT (this may be an issue, check your code!)", val.getClass().getCanonicalName());
                throw new RuntimeException("Duplicate lazy initialization with non idempotent factory");
            }
        }
        return val;
    }
}
