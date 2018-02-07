package com.arvatosystems.t9t.base;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Marker interface used by reflections. */
public interface IViewModelContainer {
    /** A lookup to retrieve the factory classes for CRUD operations by ID. */
    public static final Map<String, CrudViewModel<?,?>> CRUD_VIEW_MODEL_REGISTRY = new ConcurrentHashMap<String, CrudViewModel<?,?>>(100);

    /** A lookup to retrieve the view model ID by grid ID. */
    public static final Map<String, String> VIEW_MODEL_BY_GRID_ID_REGISTRY = new ConcurrentHashMap<String, String>(100);
}
