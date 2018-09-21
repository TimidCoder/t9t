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
package com.arvatosystems.t9t.bpmn2;

import java.util.Map;

/**
 * <p>
 * Callback interface triggered during process instance startup to initialize process variables.
 * </p>
 *
 * <p>
 * Implementations of this interface are requested from JDP using the workflow type as qualifier. The workflow type is
 * retrieved from the start event property using the key 'WorkflowType'
 * </p>
 *
 * @author TWEL006
 */
public interface IBPMNInitWorkflowCallback {

    /**
     * Initialize process instance variables.
     *
     * @param variables
     *            Modifiable map of process instance variables to initialize. This map also already contains the initial
     *            process instance variables as provided by starting message event.
     * @param properties
     *            Unmodifiable map of workflow parameters as defined on start event activity.
     */
    void init(Map<String, Object> variables, Map<String, String> properties);

}
