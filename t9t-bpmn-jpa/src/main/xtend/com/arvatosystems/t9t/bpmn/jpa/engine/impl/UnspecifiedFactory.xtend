package com.arvatosystems.t9t.bpmn.jpa.engine.impl

import com.arvatosystems.t9t.bpmn.IBPMObjectFactory

/** A dummy object which is used in workflow execution when no factory has been specified. */
class UnspecifiedFactory {
    public static final UnspecifiedFactory INSTANCE = new UnspecifiedFactory;
    private new() {}

    /** A factory to return the INSTANCE. */
    public static class UFactory implements IBPMObjectFactory<Object> {
        private new() {}

        override getRefForLock(Long objectRef) {
            return null // no locking desired
        }

        override read(Long objectRef, Long lockObjectRef, boolean jvmLockAcquired) {
            return INSTANCE  // returns the same instance for any parameter
        }
    }

    public static final UFactory FACTORY = new UFactory
}
