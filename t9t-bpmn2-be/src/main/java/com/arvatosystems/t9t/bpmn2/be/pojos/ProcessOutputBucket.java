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

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.arvatosystems.t9t.bpmn.pojo.ProcessOutput;

import de.jpaw.bonaparte.core.BonaPortable;

/**
 * A bucket to hold process execution output. It's only used during process execution.
 * The outputs stored during process execution will be returned as {@linkplain ProcessOutput} instead.
 * This class also contains convenience methods to create {@linkplain BonaPortable} that wraps Java primitives
 * and wrapper classes.
 * @author LIEE001
 */
public final class ProcessOutputBucket implements Serializable {

    private static final long serialVersionUID = -6112751939826311744L;

    private Map<String, BonaPortable> outputs;

    public ProcessOutputBucket() {
        this.outputs = new HashMap<>();
    }

    public void store(final String name, final BonaPortable value) {
        outputs.put(name, value);
    }

    public Map<String, BonaPortable> getOutputs() {
        return Collections.unmodifiableMap(outputs);
    }
}
