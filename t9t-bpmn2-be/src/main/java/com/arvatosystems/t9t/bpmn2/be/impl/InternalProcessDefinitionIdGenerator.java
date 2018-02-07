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
