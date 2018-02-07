package com.arvatosystems.t9t.bpmn.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.bpmn.ProcessExecutionStatusDTO
import com.arvatosystems.t9t.bpmn.jpa.entities.ProcessExecStatusEntity
import com.arvatosystems.t9t.bpmn.jpa.persistence.IProcessExecStatusEntityResolver

@AutoMap42
public class ProcessExecStatusEntityMappers {
    IProcessExecStatusEntityResolver entityResolver
    @AutoHandler("CSP42")
    def void e2dProcessExecutionStatusDTO(ProcessExecStatusEntity entity, ProcessExecutionStatusDTO dto) {}
}
