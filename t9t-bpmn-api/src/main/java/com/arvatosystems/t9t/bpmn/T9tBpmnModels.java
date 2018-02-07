package com.arvatosystems.t9t.bpmn;


import com.arvatosystems.t9t.base.CrudViewModel;
import com.arvatosystems.t9t.base.IViewModelContainer;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.bpmn.request.ProcessDefinitionCrudRequest;
import com.arvatosystems.t9t.bpmn.request.ProcessDefinitionSearchRequest;
import com.arvatosystems.t9t.bpmn.request.ProcessExecutionStatusCrudRequest;
import com.arvatosystems.t9t.bpmn.request.ProcessExecutionStatusSearchRequest;

public class T9tBpmnModels implements IViewModelContainer {
    public static final CrudViewModel<ProcessDefinitionDTO, FullTrackingWithVersion> PROCESS_DEFINITION_VIEW_MODEL = new CrudViewModel<ProcessDefinitionDTO, FullTrackingWithVersion>(
        ProcessDefinitionDTO.BClass.INSTANCE,
        FullTrackingWithVersion.BClass.INSTANCE,
        ProcessDefinitionSearchRequest.BClass.INSTANCE,
        ProcessDefinitionCrudRequest.BClass.INSTANCE);
    public static final CrudViewModel<ProcessExecutionStatusDTO, FullTrackingWithVersion> PROCESS_STATUS_VIEW_MODEL = new CrudViewModel<ProcessExecutionStatusDTO, FullTrackingWithVersion>(
        ProcessExecutionStatusDTO.BClass.INSTANCE,
        FullTrackingWithVersion.BClass.INSTANCE,
        ProcessExecutionStatusSearchRequest.BClass.INSTANCE,
        ProcessExecutionStatusCrudRequest.BClass.INSTANCE);

    static {
        IViewModelContainer.CRUD_VIEW_MODEL_REGISTRY.putIfAbsent("processDefinition", PROCESS_DEFINITION_VIEW_MODEL);
        IViewModelContainer.CRUD_VIEW_MODEL_REGISTRY.putIfAbsent("bpmnStatus",        PROCESS_STATUS_VIEW_MODEL);
    }
}
