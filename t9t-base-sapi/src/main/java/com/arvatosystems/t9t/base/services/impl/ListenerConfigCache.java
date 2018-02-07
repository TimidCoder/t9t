package com.arvatosystems.t9t.base.services.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.arvatosystems.t9t.base.types.ListenerConfig;

/** The cache for JPA listener configuration.
 * The cache is a double nested Map, the first level addressing the classification (one element per JPA entity)
 * and this is never empty for existing entity classes (it is created either when the entity listener is created or
 * when the cache is loaded).
 * The elements are concurrent hash maps.
 * The second level maps tenantRefs to the cached configurations.
 * This level is updated with the ListenerConfig CRUD command.
 * The second level may be empty (inactive DTO entries correspond to non existing entries here). */

public class ListenerConfigCache {
    private static final ConcurrentMap<String, ConcurrentMap<Long, ListenerConfig>> LISTENER_CONFIG = new ConcurrentHashMap<String, ConcurrentMap<Long, ListenerConfig>>();

    /** Returns the unique registration map for a given classification.
     * Creates an entry if none exists.
     * Never returns null. Unfortunately the Java 8 implementation is not lock free.
     */
    public static ConcurrentMap<Long, ListenerConfig> getRegistrationForClassification(String classification) {
        // "old" Java 7 way... complicated...
//        final ConcurrentMap<Long, ListenerConfig> existingMap = LISTENER_CONFIG.get(classification);
//        if (existingMap != null)
//            return existingMap;  // fast track - executed for all calls except the first (or first few in case of
//        final ConcurrentMap<Long, ListenerConfig> newMap = new ConcurrentHashMap<>();
//        final ConcurrentMap<Long, ListenerConfig> mapCreatedByRaceCondition = LISTENER_CONFIG.putIfAbsent(classification, newMap);
//        return mapCreatedByRaceCondition != null ? mapCreatedByRaceCondition : newMap;
        // all built-in with Java 8, no more race conditions, never creates a map which is thrown away
        return LISTENER_CONFIG.computeIfAbsent(classification, (k) -> new ConcurrentHashMap<Long, ListenerConfig>());
    }

    /** Updates an entry or deletes it (if newEntry == null) */
    public static void updateRegistration(String classification, Long tenantRef, ListenerConfig newEntry) {
        // step 1: get the entry for the given classification
        ConcurrentMap<Long, ListenerConfig> existingMap = getRegistrationForClassification(classification);
        if (newEntry == null)
            existingMap.remove(tenantRef);
        else
            existingMap.put(tenantRef, newEntry);
    }
}
