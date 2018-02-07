package com.arvatosystems.t9t.core;

import java.util.Arrays;
import java.util.List;

import com.arvatosystems.t9t.base.ILeanGridConfigContainer;

public class T9tCoreLeanGridConfig implements ILeanGridConfigContainer {

    private static String [] GRID_CONFIGS = {
        "statistics",
        "processStatus",
        "componentInfo",
        "subscriberConfig",
        "listenerConfig",
        "sliceTracking",
        "cannedRequest"
    };

    @Override
    public List<String> getResourceNames() {
        return Arrays.asList(GRID_CONFIGS);
    }
}
