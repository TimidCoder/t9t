package com.arvatosystems.t9t.bpmn.pojo;

import java.io.InputStream;

/**
 * Represents a process definition. A process definition contains:
 * <ul>
 *  <li>id of the process definition</li>
 *  <li>input stream from which the process definition will be loaded</li>
 * </ul>
 * @author LIEE001
 */
public final class ProcessDefinition {

    private String id;
    private InputStream inputStream;

    public ProcessDefinition(final String id, final InputStream inputStream) {
        this.id = id;
        this.inputStream = inputStream;
    }

    public String getId() {
        return id;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
