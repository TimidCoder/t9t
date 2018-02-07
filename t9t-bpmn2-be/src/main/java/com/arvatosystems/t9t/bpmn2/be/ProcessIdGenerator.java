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
