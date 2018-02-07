package com.arvatosystems.t9t.base;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.arvatosystems.t9t.base.uiprefs.UIGridPreferences;

/** Marker interface, should be implemented by classes which contribute to the default grid configuration settings. */
public interface IGridConfigContainer {
    public static final Map<String, UIGridPreferences> GRID_CONFIG_REGISTRY = new ConcurrentHashMap<String, UIGridPreferences>(100);
}
