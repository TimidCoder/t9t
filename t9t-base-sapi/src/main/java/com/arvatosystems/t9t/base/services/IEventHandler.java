package com.arvatosystems.t9t.base.services;

import com.arvatosystems.t9t.base.event.EventParameters;

/** Describes methods of Event handlers (subscribers to events).
 * Implementations should be singletons and be annotated by a @Named annotation, by which the implementation is looked up. */
public interface IEventHandler {

    /** Perform activity for a trigger described by eventData.
     * Returns 0 is processing was successful, otherwise an error code. */
    public int execute(RequestContext ctx, EventParameters eventData);
}
