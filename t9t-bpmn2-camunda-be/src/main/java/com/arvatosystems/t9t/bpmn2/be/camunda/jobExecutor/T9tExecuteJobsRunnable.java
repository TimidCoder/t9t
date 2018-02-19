/*
 * Copyright (c) 2012 - 2018 Arvato Systems GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arvatosystems.t9t.bpmn2.be.camunda.jobExecutor;

import static com.arvatosystems.t9t.bpmn2.be.camunda.BPMN2Constants.PROPERTY_API_KEY;
import static com.arvatosystems.t9t.bpmn2.be.camunda.utils.IdentifierConverter.bpmnTenantIdToT9tTenantRef;
import static java.util.Arrays.asList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Runnable to execute given job id, called by BPMN engine. It will group the given job ids by their tenant and pass
 * them to the {@link JobExecutionRequestWrapper} to perform execution within a T9T request context.
 *
 * @author TWEL006
 */
public class T9tExecuteJobsRunnable implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(T9tExecuteJobsRunnable.class);

    private final List<String> jobIdsToExecute;
    private final ProcessEngine processEngine;
    private final JobExecutionRequestWrapper jobExecutionRequestWrapper;

    public T9tExecuteJobsRunnable(List<String> jobIdsToExecute, ProcessEngine processEngine, JobExecutionRequestWrapper jobExecutionRequestWrapper) {
        this.jobIdsToExecute = jobIdsToExecute;
        this.processEngine = processEngine;
        this.jobExecutionRequestWrapper = jobExecutionRequestWrapper;
    }

    @Override
    public void run() {
        MDC.clear();
        LOGGER.debug("Try to execute {} jobs", jobIdsToExecute.size());

        if (jobIdsToExecute.isEmpty()) {
            return;
        }

        final ManagementService managementService = processEngine.getManagementService();

        for (String jobId : jobIdsToExecute) {
            final Job job = managementService.createJobQuery()
                                             .jobId(jobId)
                                             .singleResult();
            final Long tenantRef = bpmnTenantIdToT9tTenantRef(job.getTenantId());
            final UUID apiKey = getApiKeyForJob(job);

            jobExecutionRequestWrapper.executeJobs(asList(jobId), tenantRef, apiKey);
        }
    }

    /**
     * Get API key, which is defined on process definition level.
     *
     * @param job
     *            Job to get API key for
     *
     * @return API key or NULL if none is configured
     */
    private UUID getApiKeyForJob(Job job) {
        final RepositoryService repositoryService = processEngine.getRepositoryService();

        // We have to navigate the BPMN model, which can be NULL at any point, thus using Optional
        return Optional.of(job.getProcessDefinitionId())
                       // First get BPMN Model
                       .map(processDefinitionId -> repositoryService.getBpmnModelInstance(processDefinitionId))
                       // Get process element of model
                       .map(model -> model.getModelElementById(job.getProcessDefinitionKey()))
                       .filter(element -> element instanceof Process)
                       // Get extensions of process
                       .map(process -> ((Process) process).getExtensionElements())
                       // Now search all CamundaProperties for APIKey
                       .flatMap(extension -> extension.getElementsQuery()
                                                      .filterByType(CamundaProperties.class)
                                                      .list()
                                                      .stream()
                                                      .flatMap(properties -> properties.getCamundaProperties()
                                                                                       .stream())
                                                      .filter(property -> PROPERTY_API_KEY.equals(property.getCamundaName()))
                                                      // And provide an UUID of its value
                                                      .map(property -> UUID.fromString(property.getCamundaValue()))
                                                      .findFirst())
                       .orElse(null);
    }

}
