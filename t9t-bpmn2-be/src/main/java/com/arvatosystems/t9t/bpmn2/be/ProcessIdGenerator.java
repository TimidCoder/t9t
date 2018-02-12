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

import com.arvatosystems.t9t.base.T9tConstants;

public class ProcessIdGenerator {

    public static String generateId(String tenantId, String processDefinitionId) {
        // we can't seem to have "@" as process name because it is NOT allowed in NCNames characters
        if (T9tConstants.GLOBAL_TENANT_ID.equals(tenantId)) {
            return processDefinitionId;
        } else {
            return tenantId + "_" + processDefinitionId;
        }
    }
}
