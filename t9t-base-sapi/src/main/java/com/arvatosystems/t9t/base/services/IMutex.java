package com.arvatosystems.t9t.base.services;

import java.util.function.Supplier;

/** interface to allow synchronizing on a specific object, referenced by objectRef. */
@FunctionalInterface
public interface IMutex<T> {
    public T runSynchronizedOn(Long objectRef, Supplier<T> code);
}
