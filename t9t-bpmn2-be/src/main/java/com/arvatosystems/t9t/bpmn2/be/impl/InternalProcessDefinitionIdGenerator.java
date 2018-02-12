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
package com.arvatosystems.t9t.bpmn2.be.impl;

import com.arvatosystems.t9t.base.T9tConstants;

/**
 * Responsible for generation of internal process id used by FortyTwo.
 * @author LIEE001
 */
public class InternalProcessDefinitionIdGenerator {

    private static final String ID_FORMAT = "%s_%s";

    private InternalProcessDefinitionIdGenerator() {
        // private constructor
    }

    public static String generateId(final String tenantId, final String processDefinitionId) {
        if (T9tConstants.GLOBAL_TENANT_ID.equals(tenantId)) {
            return processDefinitionId;
        } else {
            return String.format(ID_FORMAT, tenantId, processDefinitionId);
        }
    }
}
