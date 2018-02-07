package com.arvatosystems.t9t.base;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.arvatosystems.t9t.base.uiprefs.UILeanGridPreferences;

public interface ILeanGridConfigContainer {
    public static final Map<String, UILeanGridPreferences> LEAN_GRID_CONFIG_REGISTRY = new ConcurrentHashMap<String, UILeanGridPreferences>(100);

    /** Method to return the list of resources (JSON files in the file system) which contain the default grid configuration.
     * @return List of configuration names.
     */
    List<String> getResourceNames();
}
