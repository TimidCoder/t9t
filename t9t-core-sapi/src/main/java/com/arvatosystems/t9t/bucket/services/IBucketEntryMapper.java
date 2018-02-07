package com.arvatosystems.t9t.bucket.services;

import java.util.List;
import java.util.Map;

import com.arvatosystems.t9t.base.services.IOutputSession;

/** Interface, implementations convert from a BucketEntry to a DataWithTrackingAndMore.
 * Implementations are singletons, @Named with a qualifier. */
public interface IBucketEntryMapper {
    default boolean alwaysNeedModes() { return false; }
    void writeEntities(List<Long> refs, Map<Long, Integer> entries, boolean withTrackingAndMore, IOutputSession os);
}
