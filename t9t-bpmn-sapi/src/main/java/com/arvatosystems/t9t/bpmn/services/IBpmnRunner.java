package com.arvatosystems.t9t.bpmn.services;

import com.arvatosystems.t9t.base.services.RequestContext;

public interface IBpmnRunner {
    /** Perform workflow steps. Returns true if the workflow should rerun immediately (intermediate commit), false in any other case. */
    boolean run(RequestContext ctx, Long statusRef);
}
