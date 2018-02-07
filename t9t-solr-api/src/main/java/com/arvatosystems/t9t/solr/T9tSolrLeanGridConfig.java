package com.arvatosystems.t9t.solr;

import java.util.Arrays;
import java.util.List;

import com.arvatosystems.t9t.base.ILeanGridConfigContainer;

public class T9tSolrLeanGridConfig implements ILeanGridConfigContainer {

    private static String [] GRID_CONFIGS = {
        "solrModuleCfg"
    };

    @Override
    public List<String> getResourceNames() {
        return Arrays.asList(GRID_CONFIGS);
    }
}
