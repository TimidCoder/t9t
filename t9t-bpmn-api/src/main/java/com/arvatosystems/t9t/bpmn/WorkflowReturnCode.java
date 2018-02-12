/*
 * Copyright (c) 2012 - 2018 Arvato Systems GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arvatosystems.t9t.bpmn;

public enum WorkflowReturnCode {
    PROCEED_NEXT,                   // execute next step in same transaction
    COMMIT_RESTART,                 // end this transaction, but launch immediate further processing
    YIELD,                          // commit and end for now, but the workflow will be resumed later (after some time gate probably). The current step will be executed again.
    YIELD_NEXT,                     // commit and end for now, but the workflow will be resumed later (after some time gate probably). The next step will be executed. (It is like COMMIT_RESTART with a delay).
    ERROR,                          // execution encountered an error, stored returnCode and maybe errorDetails. Execution will not proceed
    DONE                            // mark the workflow as completed, the entry is deleted from the workflow processing table
}
