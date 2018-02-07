package com.arvatosystems.t9t.bpmn;

public enum WorkflowRunnableCode {
    RUN,                            // run this workflow step
    SKIP,                           // do not run this step, but try next (maybe this one was executed already)
    ERROR,                          // execution encountered an error, stored returnCode and maybe errorDetails. Execution will not proceed
    YIELD                           // no further processing in this transaction
}
