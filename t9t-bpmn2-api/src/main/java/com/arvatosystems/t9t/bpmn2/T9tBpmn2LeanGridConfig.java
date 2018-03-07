package com.arvatosystems.t9t.bpmn2;

import java.util.Arrays;
import java.util.List;

import com.arvatosystems.t9t.base.ILeanGridConfigContainer;

public class T9tBpmn2LeanGridConfig implements ILeanGridConfigContainer {

    private static String [] GRID_CONFIGS = {
        "processDefinitionBpmn2",
        "processInstanceBpmn2",
        "processInstance.eventSubscriptions",
        "processInstance.incidents"
    };

    @Override
    public List<String> getResourceNames() {
        return Arrays.asList(GRID_CONFIGS);
    }
}
