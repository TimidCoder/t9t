package com.arvatosystems.t9t.bpmn;

public enum WorkflowReturnCode {
    PROCEED_NEXT,                   // execute next step in same transaction
    COMMIT_RESTART,                 // end this transaction, but launch immediate further processing
    YIELD,                          // commit and end for now, but the workflow will be resumed later (after some time gate probably). The current step will be executed again.
    YIELD_NEXT,                     // commit and end for now, but the workflow will be resumed later (after some time gate probably). The next step will be executed. (It is like COMMIT_RESTART with a delay).
    ERROR,                          // execution encountered an error, stored returnCode and maybe errorDetails. Execution will not proceed
    DONE                            // mark the workflow as completed, the entry is deleted from the workflow processing table
}
