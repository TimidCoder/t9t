package com.arvatosystems.t9t.bucket.services;

import java.util.Map;

import com.arvatosystems.t9t.base.event.BucketWriteKey;

public interface IBucketPersistenceAccess {
    void open();
    void write(Map<BucketWriteKey, Integer> entries);
    void close();
}
