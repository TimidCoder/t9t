package com.arvatosystems.t9t.misc.extensions

import com.arvatosystems.t9t.base.ITestConnection
import com.arvatosystems.t9t.base.crud.CrudSurrogateKeyResponse
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion
import com.arvatosystems.t9t.bpmn.ProcessDefinitionDTO
import com.arvatosystems.t9t.bpmn.ProcessDefinitionKey
import com.arvatosystems.t9t.bpmn.T9tAbstractWorkflowStep
import com.arvatosystems.t9t.bpmn.T9tWorkflow
import com.arvatosystems.t9t.bpmn.T9tWorkflowStepJavaTask
import com.arvatosystems.t9t.bpmn.request.ProcessDefinitionCrudRequest
import com.arvatosystems.t9t.io.DataSinkDTO
import de.jpaw.bonaparte.pojos.api.OperationType
import java.util.ArrayList
import java.util.List

class BpmExtensions {

    // extension methods for the types with surrogate keys
    def static CrudSurrogateKeyResponse<DataSinkDTO, FullTrackingWithVersion> merge(ProcessDefinitionDTO dto, ITestConnection dlg) {
        dto.validate
        return dlg.typeIO(new ProcessDefinitionCrudRequest => [
            crud            = OperationType.MERGE
            data            = dto
            naturalKey      = new ProcessDefinitionKey(dto.processDefinitionId)
        ], CrudSurrogateKeyResponse)
    }

    def static T9tWorkflow toBasicWorkflowOfJavaSteps(List<String> stepNames) {
        val stepList = new ArrayList<T9tAbstractWorkflowStep>(stepNames.size)
        var int stepNo = 0
        for (s : stepNames) {
            stepNo += 10
            val step      = new T9tWorkflowStepJavaTask
            step.label    = String.format("L%03d", stepNo)
            step.stepName = s
            stepList.add(step)
        }
        return new T9tWorkflow => [
            steps = stepList
        ]
    }
}
