package com.arvatosystems.t9t.bpmn.services;

import java.util.Map;

import com.arvatosystems.t9t.bpmn.IBPMObjectFactory;
import com.arvatosystems.t9t.bpmn.IWorkflowStep;

public interface IWorkflowStepCache {
    void loadCaches();
    IWorkflowStep<?> getWorkflowStepForName(String name);
    IBPMObjectFactory<?> getBPMObjectFactoryForName(String name);

    /** Returns an immutable map of all eligible steps. */
    Map<String,IWorkflowStep> getAllSteps();

    /** Returns an immutable map of all eligible factories. */
    Map<String,IBPMObjectFactory> getAllFactories();
}
