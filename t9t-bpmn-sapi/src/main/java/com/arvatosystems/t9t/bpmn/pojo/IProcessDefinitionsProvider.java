package com.arvatosystems.t9t.bpmn.pojo;

import java.util.Set;

/**
 * Provides a mechanism for each module to declare a set of {@linkplain ProcessDefinition}
 * which it might use during its life cycle e.g. execution of specific request calls/execute specific process.
 * @author LIEE001
 */
public interface IProcessDefinitionsProvider {

    /**
     * Returns a set of process definitions used.
     *
     * @return process definitions
     */
    Set<ProcessDefinition> getProcessDefinitions();
}
