package com.arvatosystems.t9t.base.services;

import java.io.Closeable;
import java.util.Map;

import com.arvatosystems.t9t.base.event.BucketWriteKey;

/** A queue to write buckets. */
public interface IBucketWriter extends Closeable {
    /** Opens the output, issued as first call in regular lifecycle. */
    void open();

    /** Write to a set of buckets. */
    void writeToBuckets(Map<BucketWriteKey, Integer> cmds);

    /** Flushes unwritten buffers immediately - perform to sync.
     * Only required if the writer buffers data, otherwise a no-op. */
    default void flush() {}

    /** Shuts down the writer - last call in regular lifecycle. */
    @Override
    void close();
}
