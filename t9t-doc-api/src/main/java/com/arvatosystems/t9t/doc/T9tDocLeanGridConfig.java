package com.arvatosystems.t9t.doc;

import java.util.Arrays;
import java.util.List;

import com.arvatosystems.t9t.base.ILeanGridConfigContainer;

public class T9tDocLeanGridConfig implements ILeanGridConfigContainer {

    private static String [] GRID_CONFIGS = {
        "docConfig",
        "docTemplate",
        "docEmailCfg",
        "docComponent",
        "docModuleCfg"
    };

    @Override
    public List<String> getResourceNames() {
        return Arrays.asList(GRID_CONFIGS);
    }
}
