package com.arvatosystems.t9t.base.services;

import com.arvatosystems.t9t.base.event.BucketDataMode;

/** Depending on configuration, throws an event or creates a bucket.
 * Corresponds to JPA entity listeners, but for explicitly generated events. */
public interface IEventOrBucketCreator {
    /** Entry to be used when no request context is available (slower convenience function). */
    void createBucketOrEvent(String bucketId, Long ref, BucketDataMode mode);

    /** Preferred entry point, uses existin context. */
    void createBucketOrEvent(RequestContext ctx, String bucketId, Long ref, BucketDataMode mode);
}
