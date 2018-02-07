package com.arvatosystems.t9t.bpmn.services;

import com.arvatosystems.t9t.bpmn.ProcessDefinitionDTO;

public interface IProcessDefinitionCache {
    ProcessDefinitionDTO getCachedProcessDefinitionDTO(String tenantId, String processDefinitionId);
}
