package com.arvatosystems.t9t.base.be.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.T9tException;
import com.arvatosystems.t9t.base.services.IMutex;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import de.jpaw.dp.Singleton;
import de.jpaw.util.ApplicationException;
import de.jpaw.util.ExceptionUtil;

@Singleton
public class MutexSingleJVM<T> implements IMutex<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MutexSingleJVM.class);
    static private final Cache<Long,Object> ACTIVE_MUTEXES = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.MINUTES).build();

    @Override
    public T runSynchronizedOn(final Long objectRef, final Supplier<T> code) {

        try {
            // acquire the lock. For every objectRef there is a separate lock
            final Object lock = ACTIVE_MUTEXES.get(objectRef, () -> { return new Object(); } );
            synchronized (lock) {
                return code.get();
            }
        } catch (ExecutionException e) {
            LOGGER.error("running synchronized exited with {}", ExceptionUtil.causeChain(e));
            if (e.getCause() instanceof ApplicationException)
                throw (ApplicationException)e.getCause();
            throw new T9tException(T9tException.GENERAL_EXCEPTION, e.getCause());
        }
    }
}
