package com.arvatosystems.t9t.ssm;

import java.util.Arrays;
import java.util.List;

import com.arvatosystems.t9t.base.ILeanGridConfigContainer;

public class T9tSsmLeanGridConfig implements ILeanGridConfigContainer {

    private static String [] GRID_CONFIGS = {
        "schedulerSetup"
    };

    @Override
    public List<String> getResourceNames() {
        return Arrays.asList(GRID_CONFIGS);
    }
}
