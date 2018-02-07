package com.arvatosystems.t9t.base.services;

import java.util.HashMap;
import java.util.Map;

import de.jpaw.bonaparte.pojos.apiw.Ref;

public final class MapperCache {
    /** Produces a field path name from a given prefix and a field name. */
    public static final String concat(String prefix, String field) {
        return prefix == null ? field : (prefix + "." + field);
    }
    public static final Map<Long, Ref> getCache(Map<String, Map<Long, Ref>> cache, String pqon) {
        return cache.computeIfAbsent(pqon, (x) -> new HashMap<Long, Ref>());
    }
    public static final Map<Long, Ref> getCache(Map<String, Map<Long, Ref>> cache, String pqon, int initialSize) {
        return cache.computeIfAbsent(pqon, (x) -> new HashMap<Long, Ref>(initialSize * 2));
    }
    public static final <V extends Ref> Map<Long, V> getCache(Map<String, Map<Long, Ref>> cache, Class<V> dtoCls, int initialSize) {
        return (Map<Long, V>) cache.computeIfAbsent(dtoCls.getSimpleName(), (x) -> new HashMap<Long, Ref>(initialSize * 2));
    }
    public static final void addToCache(Map<String, Map<Long, Ref>> cache, String pqon, Long key, Ref dto) {
        getCache(cache, pqon).put(key, dto);
    }
    public static final Ref getFromCache(Map<String, Map<Long, Ref>> cache, String pqon, Long key) {
        return getCache(cache, pqon).get(key);
    }
}
