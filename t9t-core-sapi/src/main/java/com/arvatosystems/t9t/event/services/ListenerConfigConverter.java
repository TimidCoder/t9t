package com.arvatosystems.t9t.event.services;

import java.util.Set;

import com.arvatosystems.t9t.base.types.ListenerConfig;
import com.arvatosystems.t9t.event.ListenerConfigDTO;
import com.google.common.collect.ImmutableSet;

public class ListenerConfigConverter {
    public static Set<String> csvToSet(String buckets) {
        if (buckets == null || buckets.length() == 0)
            return null;
        String [] bucketArray = buckets.split(",");
        return ImmutableSet.copyOf(bucketArray);
    }

    public static ListenerConfig convert(ListenerConfigDTO cfg) {
        if (!cfg.getIsActive())
            return null;

        return new ListenerConfig(
            cfg.getIssueCreatedEvents(),
            cfg.getIssueDeletedEvents(),
            cfg.getIssueUpdatedEvents(),
            cfg.getIssueSecondEvents(),
            cfg.getIssueThirdEvents(),
            csvToSet(cfg.getCreationBuckets()),
            csvToSet(cfg.getDeletionBuckets()),
            csvToSet(cfg.getUpdateBuckets()),
            csvToSet(cfg.getSecondBuckets()),
            csvToSet(cfg.getThirdBuckets())
        );
    }
}
