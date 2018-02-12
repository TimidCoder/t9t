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
package com.arvatosystems.t9t.bpmn2.be;

import java.io.Serializable;

import com.arvatosystems.t9t.bpmn.WorkflowReturnCode;

// migrated for workflow V2
 public interface IBPMObject extends Serializable {
    /**
     * Returns the target object reference.
     * @return target object reference
     */
    Long getObjectRef();

    /**
     * Returns the workflow return code.
     * @return workflow return code
     */
    WorkflowReturnCode getWorkflowReturnCode();

    /**
     * Set a new workflow return code.
     * @param workflowReturnCode given new workflow return code
     */
    void setWorkflowReturnCode(WorkflowReturnCode workflowReturnCode);
}
