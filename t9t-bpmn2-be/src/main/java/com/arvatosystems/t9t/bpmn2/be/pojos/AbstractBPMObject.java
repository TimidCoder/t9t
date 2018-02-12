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
package com.arvatosystems.t9t.bpmn2.be.pojos;

import com.arvatosystems.t9t.bpmn.WorkflowReturnCode;
import com.arvatosystems.t9t.bpmn2.be.IBPMObject;

/**
 * @author LOWM005
 * @author NGTZ001 migrated for workflow V2
 */
public abstract class AbstractBPMObject implements IBPMObject {

    private WorkflowReturnCode workflowReturnCode;

    @Override
    public WorkflowReturnCode getWorkflowReturnCode() {
        return workflowReturnCode;
    }

    @Override
    public void setWorkflowReturnCode(final WorkflowReturnCode workflowReturnCode) {
        this.workflowReturnCode = workflowReturnCode;
    }
}
