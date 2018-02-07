package com.arvatosystems.t9t.bpmn2.be.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.auth.TenantRef;
import com.arvatosystems.t9t.auth.jpa.entities.TenantEntity;
import com.arvatosystems.t9t.auth.jpa.persistence.ITenantEntityResolver;
import com.arvatosystems.t9t.base.T9tConstants;
import com.arvatosystems.t9t.base.T9tException;
import com.arvatosystems.t9t.base.services.IFileUtil;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.bpmn.ProcessDefinitionDTO;
import com.arvatosystems.t9t.bpmn.ProcessDefinitionKey;
import com.arvatosystems.t9t.bpmn.ProcessDefinitionRef;
import com.arvatosystems.t9t.bpmn.T9tBPMException;
import com.arvatosystems.t9t.bpmn.WorkflowReturnCode;
import com.arvatosystems.t9t.bpmn.jpa.entities.ProcessDefinitionEntity;
import com.arvatosystems.t9t.bpmn.jpa.persistence.IProcessDefinitionEntityResolver;
import com.arvatosystems.t9t.bpmn.pojo.ProcessDefinition;
import com.arvatosystems.t9t.bpmn.pojo.ProcessOutput;
import com.arvatosystems.t9t.bpmn.services.IBPMService;
import com.arvatosystems.t9t.bpmn.services.IBpmnPersistenceAccess;
import com.arvatosystems.t9t.bpmn.services.IWorkflowStepCache;
import com.arvatosystems.t9t.bpmn2.be.IBPMObject;
import com.arvatosystems.t9t.bpmn2.be.IBPMServiceImplementation;
import com.arvatosystems.t9t.bpmn2.be.ProcessIdGenerator;
import com.arvatosystems.t9t.bpmn2.be.deployer.ProcessDefinitionContentDecorator;
import com.arvatosystems.t9t.bpmn2.be.pojos.DeployResourceBean;
import com.arvatosystems.t9t.bpmn2.be.pojos.ProcessOutputBucket;
import com.arvatosystems.t9t.cfg.be.T9tServerConfiguration;
import com.google.common.base.MoreObjects;
import com.google.common.io.ByteStreams;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Provider;
import de.jpaw.dp.Singleton;

/**
 * Implementation of {@linkplain IBPMService} as a bridging of real BPM
 * implementation either activiti or camunda.
 *
 * @author LIEE001
 */
@Singleton
public class BPMService implements IBPMService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BPMService.class);

    private static final String BPMN_FOLDER_NAME = "bpmn";
    private static final String PARAMETER_OUTPUT = "output";
    private static final String DEFAULT_BPM_ENGINE = "activiti";

    private final IWorkflowStepCache stepCache = Jdp.getRequired(IWorkflowStepCache.class);
    private final ProcessDefinitionContentDecorator processDefinitionContentDecorator = Jdp.getRequired(ProcessDefinitionContentDecorator.class);
    private final IBpmnPersistenceAccess dpl = Jdp.getRequired(IBpmnPersistenceAccess.class);
    private final Provider<RequestContext> ctx = Jdp.getProvider(RequestContext.class); // only for lookup of tenant ID
    private final IProcessDefinitionEntityResolver processDefinitionEntityResolver = Jdp.getRequired(IProcessDefinitionEntityResolver.class);
    private final ITenantEntityResolver tenantResolver = Jdp.getRequired(ITenantEntityResolver.class);
    private final IFileUtil fileUtil = Jdp.getRequired(IFileUtil.class);

    private final IBPMServiceImplementation bpmServiceHandler;

    public BPMService() {
        T9tServerConfiguration serverConfiguration = Jdp.getRequired(T9tServerConfiguration.class);
        String bpmEngineName = MoreObjects.firstNonNull(serverConfiguration.bpmConfiguration.engine, DEFAULT_BPM_ENGINE);
        bpmServiceHandler = Jdp.getRequired(IBPMServiceImplementation.class, bpmEngineName);
        LOGGER.info("Initializing BPM Service (engine={})", bpmEngineName);

        // Map<String, IWorkflowStep> businessProcessBeans = stepCache.getAllSteps();
        bpmServiceHandler.init(stepCache.getAllSteps()); // reuses the standard BPM cache
        LOGGER.info("BPM Service initialized");
    }

    private boolean isProcessDefinitionExist(final ProcessDefinitionRef processDefinitionRef) {
        boolean processExist = true;
        try {
            processDefinitionEntityResolver.getEntityData(processDefinitionRef, false);
        } catch (T9tException ex) {
            processExist = false;
        }

        return processExist;
    }

    private ProcessDefinitionEntity getProcessDefinitionEntity(final ProcessDefinitionRef processDefinitionRef) {
        try {
            return processDefinitionEntityResolver.getEntityData(processDefinitionRef, true);
        } catch (T9tException ex) {
            LOGGER.error("Can't found process definition with the given process definition reference (processDefinitionRef={})", processDefinitionRef);
            LOGGER.error("Root exception", ex);
            throw new T9tBPMException(T9tBPMException.BPM_PROCESS_DEFINITION_NOT_EXIST, "processDefinitionRef=" + processDefinitionRef);
        }
    }

    private TenantEntity getTenant(final Long tenantRef) {
        try {
            return tenantResolver.getEntityData(new TenantRef(tenantRef), true);
        } catch (T9tException e) {
            LOGGER.error("An error occurred while searching for tenant with tenantRef {}.", tenantRef, e);
            LOGGER.error("Root exception", e);
            throw new T9tBPMException(T9tException.TENANT_NOT_FOUND);
        }
    }

    private String findDeploymentByProcessDefinitionId(final String processDefinitionId) {
        return bpmServiceHandler.findDeploymentByProcessDefinitionId(processDefinitionId);
    }

    private String findDeploymentByTenantRefNProcessDefinitionId(final Long tenantRef, final String processDefinitionId) {
        String internalProcessId = null;
        if (T9tConstants.GLOBAL_TENANT_REF42.equals(tenantRef)) {
            internalProcessId = processDefinitionId;
        } else {
            String tenantId = getTenant(tenantRef).getTenantId();
            internalProcessId = InternalProcessDefinitionIdGenerator.generateId(tenantId, processDefinitionId);
        }

        return findDeploymentByProcessDefinitionId(internalProcessId);
    }

    private void deployProcessInternal(final Long tenantRef, final ProcessDefinition processDefinition) {

        String tenantId = (T9tConstants.GLOBAL_TENANT_REF42.equals(tenantRef) ? T9tConstants.GLOBAL_TENANT_ID : getTenant(tenantRef).getTenantId());

        String processDefinitionId = processDefinition.getId();
        LOGGER.info("Deploying process definition {}", processDefinitionId);

        String deploymentName = InternalProcessDefinitionIdGenerator.generateId(tenantId, processDefinitionId);

        InputStream inputStream = processDefinition.getInputStream();
        if (!T9tConstants.GLOBAL_TENANT_REF42.equals(tenantRef)) {
            inputStream = processDefinitionContentDecorator.decorateProcessDefinitionContent(inputStream, tenantId, processDefinitionId);
        } else {
            inputStream = processDefinitionContentDecorator.decorateProcessDefinitionContent(inputStream, processDefinitionId);
        }

        DeployResourceBean deployResourceBean = new DeployResourceBean();
        deployResourceBean.setTenantId(tenantId);
        deployResourceBean.setProcessDefinition(processDefinition);
        deployResourceBean.setInputStream(inputStream);
        deployResourceBean.setDeploymentName(deploymentName);
        deployResourceBean.setContentResource(CONTENT_RESOURCE);

       this.bpmServiceHandler.deployProcessInternal(deployResourceBean);
    }

    /**
     * Get a single process definition id based on the given process definition
     * content.
     *
     * @param processDefinitionContent
     *            process definition content
     * @return process definition id
     * @throws T9tBPMException
     *             if process definition content doesn't contain exactly 1 process
     */
    private String getSingleProcessDefinitionId(final byte[] processDefinitionContent) {
        List<String> processDefinitionIds = processDefinitionContentDecorator.getProcessDefinitionIds(new ByteArrayInputStream(processDefinitionContent));

        if (processDefinitionIds.size() != 1) {
            LOGGER.error("Attempted to deploy less/more than 1 process definition in 1 single file (found {} process definitions).", processDefinitionIds.size());
            throw new T9tBPMException(T9tBPMException.BPM_DEPLOYMENT_MULTI_PROCESS_DEFINITION);
        } else {
            return processDefinitionIds.get(0);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessDefinitionDTO deployNewProcess(final String comment, final byte[] processDefinitionContent) {
        // make sure we have exactly 1 process definition in the given content
        String processDefinitionId = getSingleProcessDefinitionId(processDefinitionContent);

        ProcessDefinitionKey processDefinitionKey = new ProcessDefinitionKey();
        processDefinitionKey.setProcessDefinitionId(processDefinitionId);

        // make sure we don't redeploy the same process twice (user should update
        // content only instead!)
        if (isProcessDefinitionExist(processDefinitionKey)) {
            LOGGER.error("An error occured during BPMN deployment. Process definition with the same id ({}) already exist.", processDefinitionId);
            throw new T9tBPMException(T9tBPMException.BPM_DEPLOYMENT_PROCESS_DEFINITION_EXIST, "processDefinitionId=" + processDefinitionId);
        }

        // create process definition configuration entry
        ProcessDefinitionDTO processDefinitionDTO = null;
        try {
            processDefinitionDTO = ctx.get().customization.newDtoInstance(ProcessDefinitionDTO.class$rtti(), ProcessDefinitionDTO.class);
            processDefinitionDTO.setIsActive(true);
            processDefinitionDTO.setProcessDefinitionId(processDefinitionId);
            processDefinitionDTO.setName(comment);
            dpl.save(processDefinitionDTO);
        } catch (T9tException ex) {
            LOGGER.error("An error occured during BPMN deployment. Failed to create process definition configuration entry.", ex);
            throw new T9tBPMException(T9tBPMException.BPM_DEPLOYMENT_ERROR);
        }

        // deploy process
        deployProcessInternal(ctx.get().tenantRef, new ProcessDefinition(processDefinitionId, new ByteArrayInputStream(processDefinitionContent)));

        return processDefinitionDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void redeployProcess(final ProcessDefinitionRef processDefinitionRef, final byte[] processDefinitionContent) {
        ProcessDefinitionEntity processDefinitionEntity = getProcessDefinitionEntity(processDefinitionRef);

        // make sure we have exactly 1 process definition in the given content
        String processDefinitionId = getSingleProcessDefinitionId(processDefinitionContent);

        // reject process definition update if referenced process definition id is NOT
        // the same with the one defined in the content i.e. XML
        if (!processDefinitionEntity.getProcessDefinitionId().equals(processDefinitionId)) {
            throw new T9tBPMException(T9tBPMException.BPM_DEPLOYMENT_PROCESS_DEFINITION_ID_NOT_IN_SYNC);
        }

        // disallow re-deploy global process definition from tenant
        if (!processDefinitionEntityResolver.writeAllowed(processDefinitionEntity.getTenantRef())) {
            throw new T9tBPMException(T9tBPMException.WRITE_ACCESS_ONLY_CURRENT_TENANT);
        }

        try {
            processDefinitionEntityResolver.save(processDefinitionEntity);
        } catch (T9tException e) {
            throw new T9tBPMException(T9tBPMException.BPM_DEPLOYMENT_TENANT_PERMISSION, processDefinitionEntity.getTenantRef().toString());
        }

        // redeploy process content
        deployProcessInternal(processDefinitionEntity.getTenantRef(), new ProcessDefinition(processDefinitionEntity.getProcessDefinitionId(), new ByteArrayInputStream(processDefinitionContent)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isProcessDeployed(final ProcessDefinitionRef processDefinitionRef) {
        try {
            // check on the configuration table
            ProcessDefinitionEntity processDefinitionEntity = processDefinitionEntityResolver.getEntityData(processDefinitionRef, true);

            // as well as Activiti deployment
            return (findDeploymentByTenantRefNProcessDefinitionId(processDefinitionEntity.getTenantRef(), processDefinitionEntity.getProcessDefinitionId()) != null);
        } catch (T9tException ex) {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deployGlobalProcess(final ProcessDefinition processDefinition) {
        deployProcessInternal(T9tConstants.GLOBAL_TENANT_REF42, processDefinition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGlobalProcessDeployed(final String processDefinitionId) {
        return (findDeploymentByProcessDefinitionId(processDefinitionId) != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessOutput executeProcess(final String processKey)  {
        return executeProcess(processKey, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProcessOutput executeProcess(final String processDefinitionId, final Map<String, ? extends Object> params)  {
        LOGGER.info("Executing process \"{}\"", processDefinitionId);

        // pass default variables which can be used inside BPMN processes:
        // - output --> instance of ProcessOutputBucket to help with storing process
        // output data
        Map<String, Object> processParams = new HashMap<>();

        ProcessOutputBucket processOutputBucket = new ProcessOutputBucket();
        processParams.put(PARAMETER_OUTPUT, processOutputBucket);

        if (params != null) {
            processParams.putAll(params);
        }

        String internalProcessId = generateInternalProcessDefinitionId(processDefinitionId);
        this.bpmServiceHandler.startProcessInstanceByKey(internalProcessId, processParams);
        return new ProcessOutput(processOutputBucket.getOutputs());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] getProcessContent(final ProcessDefinitionRef processDefinitionRef) {
        ProcessDefinitionEntity processDefinitionEntity = getProcessDefinitionEntity(processDefinitionRef);

        String processDefinitionId = processDefinitionEntity.getProcessDefinitionId();

        String deploymentId = findDeploymentByTenantRefNProcessDefinitionId(processDefinitionEntity.getTenantRef(), processDefinitionId);

        if (deploymentId == null)
            return new byte[0];

        InputStream contentStream = bpmServiceHandler.getProcessDefinitionContent(deploymentId);

        if (!T9tConstants.GLOBAL_TENANT_REF42.equals(processDefinitionEntity.getTenantRef())) {
            contentStream = processDefinitionContentDecorator.undecorateProcessDefinitionContent(contentStream);
        }

        try {
            return ByteStreams.toByteArray(contentStream);
        } catch (IOException ex) {
            LOGGER.error("Failed to get process content for {}", processDefinitionId);
            LOGGER.error("Root exception", ex);
            throw new T9tBPMException(T9tBPMException.BPM_GET_PROCESS_CONTENT_ERROR);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] getProcessDiagram(final ProcessDefinitionRef processDefinitionRef) {
        ProcessDefinitionEntity processDefinitionEntity = getProcessDefinitionEntity(processDefinitionRef);

        String processDefinitionId = processDefinitionEntity.getProcessDefinitionId();

        String deploymentId = findDeploymentByTenantRefNProcessDefinitionId(processDefinitionEntity.getTenantRef(), processDefinitionId);
        if (deploymentId == null)
            return new byte[0];

        // get the latest process definition deployment information along with it's
        // sibling process definitions if any
        InputStream diagramStream = this.bpmServiceHandler.getProcessDefinitionContent(deploymentId);

        try {
            return ByteStreams.toByteArray(diagramStream);
        } catch (IOException ex) {
            LOGGER.error("Failed to get process diagram for {}", processDefinitionId);
            LOGGER.error("Root exception", ex);
            throw new T9tBPMException(T9tBPMException.BPM_GET_PROCESS_DIAGRAM_ERROR);
        }
    }

    @Override
    public WorkflowReturnCode executeProcessSync(final String processDefinitionId, final Map<String, Object> params)  {
        LOGGER.info("Executing process \"{}\"", processDefinitionId);

        // prepare
        validateParameters(params);

        // execute
        this.bpmServiceHandler.startProcessInstanceByKey(generateInternalProcessDefinitionId(processDefinitionId), params);

        return ((IBPMObject) params.get(BPMN_WORKFLOW_DATA)).getWorkflowReturnCode();
    }

    private void validateParameters(final Map<String, ? extends Object> params) {
        if (params.get(BPMN_WORKFLOW_DATA) == null) {
            throw new T9tException(T9tException.GENERAL_EXCEPTION);
        }
    }

    private String generateInternalProcessDefinitionId(final String processDefinitionId) {
        ProcessDefinitionEntity processDefinitionEntity;
        try {
            List<ProcessDefinitionEntity> ids = processDefinitionEntityResolver.findByProcessIdWithDefault(true, processDefinitionId);
            if (ids.isEmpty()) {
                throw new T9tBPMException(T9tBPMException.BPM_PROCESS_DEFINITION_NOT_EXIST);
            }
            processDefinitionEntity = ids.get(0);
        } catch (T9tException e) {
            LOGGER.error("Cannot obtain configuration for {} in process definition configuration table", processDefinitionId);
            throw new T9tBPMException(T9tBPMException.BPM_PROCESS_DEFINITION_NOT_EXIST, processDefinitionId);
        }

        LOGGER.info("Using {} process definition for bpmId {}", processDefinitionEntity.getTenantRef() == T9tConstants.GLOBAL_TENANT_REF42 ? "global tenant" : "tenant specific", processDefinitionId);

        return (T9tConstants.GLOBAL_TENANT_REF42.equals(processDefinitionEntity.getTenantRef()) ? processDefinitionId
                : InternalProcessDefinitionIdGenerator.generateId(ctx.get().tenantId, processDefinitionId));
    }

    private void loadTenantProcess(String tenantId, ProcessDefinition processDefinition) {
        DeployResourceBean deployResourceBean = new DeployResourceBean();
        deployResourceBean.setTenantId(tenantId);
        deployResourceBean.setProcessDefinition(processDefinition);
        deployResourceBean.setInputStream(processDefinition.getInputStream());
        deployResourceBean.setDeploymentName(ProcessIdGenerator.generateId(tenantId, processDefinition.getId()));

        bpmServiceHandler.deployProcessInternal(deployResourceBean);
        LOGGER.info("Process definition {} (tenantId={}) loaded into engine", processDefinition.getId(), tenantId);
    }

    @Override
    public void loadTenantProcess(String tenantId, String processDefinitionId) {
        String processDefinitionFilePath;
        try {
            processDefinitionFilePath = getPhysicalProcessDefinitionPath(tenantId, processDefinitionId);
        } catch(T9tException ex) {
            LOGGER.error("Failed to retrieve process definition file path (tenantId={}, processDefinitionId={}).",
                    tenantId, processDefinitionId);
            throw new T9tBPMException(ex.getErrorCode());
        }

        File processDefinitionFile = new File(processDefinitionFilePath);
        if (!processDefinitionFile.exists()) {
            LOGGER.error("Failed to load tenant specific process to BPM engine (tenantId={}, processDefinitionId={}). Can't find physical process XML content.", tenantId, processDefinitionId);
            throw new T9tBPMException(T9tBPMException.BPM_PROCESS_DEFINITION_NOT_EXIST, "tenantId=" + tenantId,
                    "processDefinitionId=" + processDefinitionId);
        }

        try (InputStream inputStream = new FileInputStream(processDefinitionFile)) {
            loadTenantProcess(tenantId, new ProcessDefinition(processDefinitionId, inputStream));
        } catch (IOException ex) {
            LOGGER.error("Failed to load tenant specific process to BPM engine (tenantId={}, processDefinitionId={}).", tenantId, processDefinitionId);
            throw new T9tBPMException(T9tBPMException.BPM_DEPLOYMENT_ERROR, "tenantId=" + tenantId,
                    "processDefinitionId=" + processDefinitionId);
        }
    }

    private String getPhysicalProcessDefinitionPath(String tenantId, String processDefinitionId) {
        String tenantBpmnFolder = fileUtil.getAbsolutePath(fileUtil.buildPath(tenantId, BPMN_FOLDER_NAME));
        return fileUtil.buildPath(tenantBpmnFolder, processDefinitionId + ".bpmn20.xml");
    }

}
