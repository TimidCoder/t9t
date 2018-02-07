package com.arvatosystems.t9t.bpmn.services;

import java.util.List;

import org.joda.time.Instant;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.bpmn.ProcessDefinitionDTO;
import com.arvatosystems.t9t.bpmn.ProcessDefinitionRef;
import com.arvatosystems.t9t.bpmn.ProcessExecutionStatusDTO;
import com.arvatosystems.t9t.bpmn.request.ExecuteProcessWithRefRequest;

import de.jpaw.bonaparte.pojos.apiw.DataWithTrackingW;

public interface IBpmnPersistenceAccess {

    void save(ProcessDefinitionDTO dto);

    /** Reads a process definition using the usual fallback rule to default tenant. Throws an Exception if none exists. */
    ProcessDefinitionDTO getProcessDefinitionDTO(String processDefinitionId);

    /** Reads a process definition using the usual fallback rule for some general reference. Throws an Exception if none exists. */
    ProcessDefinitionDTO getProcessDefinitionDTO(ProcessDefinitionRef ref);

    /** Reads all process definitions. */
    List<DataWithTrackingW<ProcessDefinitionDTO, FullTrackingWithVersion>> getAllProcessDefinitionsForEngine(String engine);

    /** Stores an initial status when a new task is submitted. */
    Long persistNewStatus(ProcessExecutionStatusDTO dto);

    /** Retrieves all processDefinitions of current tasks. */
    List<ProcessExecutionStatusDTO> getTasksDue(String onlyForProcessDefinitionId, Instant whenDue, boolean includeErrorStatus);

    /** Retrieves all processDefinitions of current tasks. */
    List<Long> getTaskRefsDue(String onlyForProcessDefinitionId, Instant whenDue, boolean includeErrorStatus);

    /** Find existing process execution status */
    Long createOrUpdateNewStatus(RequestContext ctx, ProcessExecutionStatusDTO dto, ExecuteProcessWithRefRequest rq);
}
