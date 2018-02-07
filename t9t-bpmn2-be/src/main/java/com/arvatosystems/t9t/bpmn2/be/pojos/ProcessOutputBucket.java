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
