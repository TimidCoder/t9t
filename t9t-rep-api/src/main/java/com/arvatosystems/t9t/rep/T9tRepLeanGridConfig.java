package com.arvatosystems.t9t.rep;

import java.util.Arrays;
import java.util.List;

import com.arvatosystems.t9t.base.ILeanGridConfigContainer;

public class T9tRepLeanGridConfig implements ILeanGridConfigContainer {

    private static String [] GRID_CONFIGS = {
        "reportConfig",
        "reportParams",
        "reportMailing"
    };

    @Override
    public List<String> getResourceNames() {
        return Arrays.asList(GRID_CONFIGS);
    }
}
