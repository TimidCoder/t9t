package com.arvatosystems.t9t.io;

import java.util.Arrays;
import java.util.List;

import com.arvatosystems.t9t.base.ILeanGridConfigContainer;

public class T9tIOLeanGridConfig implements ILeanGridConfigContainer {

    private static String [] GRID_CONFIGS = {
        "csvConfiguration",
        "dataSinkConfig",
        "sinkSearch"
    };

    @Override
    public List<String> getResourceNames() {
        return Arrays.asList(GRID_CONFIGS);
    }
}
