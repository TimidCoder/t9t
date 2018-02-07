package com.arvatosystems.t9t.bpmn.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.bpmn.ProcessDefinitionDTO
import com.arvatosystems.t9t.bpmn.ProcessDefinitionKey
import com.arvatosystems.t9t.bpmn.jpa.entities.ProcessDefinitionEntity
import com.arvatosystems.t9t.bpmn.jpa.persistence.IProcessDefinitionEntityResolver

@AutoMap42
public class ProcessDefinitionEntityMappers {
    IProcessDefinitionEntityResolver processDefinitionResolver

    @AutoHandler("SP42")
    def void e2dProcessDefinitionDTO(ProcessDefinitionEntity entity, ProcessDefinitionDTO dto) {}
    def void e2dProcessDefinitionKey(ProcessDefinitionEntity entity, ProcessDefinitionKey dto) {}
}
