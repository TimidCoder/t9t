package com.arvatosystems.t9t.bpmn;

public interface IBPMObjectFactory<T> {
    /** Returns the object ref on which a lock should be performed while executing this workflow, or null if no locking is required. */
    Long getRefForLock(Long objectRef);

    /** Reads an object specified by its ref from disk.
     * instances are qualified by the object identifier, for example salesOrder, deliveryOrder etc.
     */
    T read(Long objectRef, Long lockObjectRef, boolean jvmLockAcquired);
}
