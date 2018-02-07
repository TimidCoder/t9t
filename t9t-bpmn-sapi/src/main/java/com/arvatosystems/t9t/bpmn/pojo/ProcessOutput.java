package com.arvatosystems.t9t.bpmn.pojo;

import java.util.Collections;
import java.util.Map;

import de.jpaw.bonaparte.core.BonaPortable;

/**
 * Represents the output of executing a particular BPMN process.
 */
public final class ProcessOutput {

    private Map<String, BonaPortable> output;

    /**
     * Creates a new ProcessOutput instance.
     *
     * @param output
     * @throws IllegalArgumentException
     *             if output is null
     */
    public ProcessOutput(final Map<String, BonaPortable> output) {
        if (output == null) {
            throw new IllegalArgumentException("Null is not allowed for constructing a ProcessOutput");
        }

        this.output = output;
    }

    /**
     * Gets the value for a given key from this process output
     *
     * @param outputKey
     *            the key for which the value is requested
     * @return the stored value for the given key. It may be null if the key is not present or if null is stored as a value for that key
     */
    public Object get(final String outputKey) {
        return output.get(outputKey);
    }

    /**
     * Get an unmodifiable copy of the internal map from this process output. Note, that only the key/value pairs are unmodifiable, the stored key- and value-objects can still be
     * changed.
     *
     * @return an unmodifiable map with all key/value pairs
     */
    public Map<String, BonaPortable> getAll() {
        return Collections.unmodifiableMap(output);
    }
}
