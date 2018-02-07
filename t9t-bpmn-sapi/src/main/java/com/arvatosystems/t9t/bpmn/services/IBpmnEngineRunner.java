package com.arvatosystems.t9t.bpmn.services;

import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.bpmn.IBPMObjectFactory;
import com.arvatosystems.t9t.bpmn.ProcessDefinitionDTO;

/** Low level runner, is invoked by engine reference. The interface is injected with the engine ID as qualifier. */
public interface IBpmnEngineRunner {
    /** Execute a process using this engine. */
    boolean run(RequestContext ctx, Long statusRef, ProcessDefinitionDTO pd, IBPMObjectFactory<?> factory, Long lockObjectRef, boolean jvmLockAcquired);
}
