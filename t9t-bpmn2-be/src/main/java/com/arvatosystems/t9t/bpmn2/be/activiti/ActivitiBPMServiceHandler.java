package com.arvatosystems.t9t.bpmn2.be.activiti;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.javax.el.ELException;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.T9tException;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.bpmn.IWorkflowStep;
import com.arvatosystems.t9t.bpmn.T9tBPMException;
import com.arvatosystems.t9t.bpmn2.be.IBPMServiceImplementation;
import com.arvatosystems.t9t.bpmn2.be.pojos.DeployResourceBean;
import com.arvatosystems.t9t.cfg.be.T9tServerConfiguration;

import de.jpaw.bonaparte.jpa.refs.PersistenceProviderJPA;
import de.jpaw.dp.Jdp;
import de.jpaw.dp.Named;
import de.jpaw.dp.Provider;
import de.jpaw.dp.Singleton;

/**
 *
 * Implementation of {@linkplain AbstractBPMServiceHandler} which uses Activiti as the underlying implementation.
 * @author WIBO001
 *
 */
@Named("activiti")
@Singleton
public class ActivitiBPMServiceHandler implements IBPMServiceImplementation {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiBPMServiceHandler.class);
    private static final String CONTENT_RESOURCE = "content.bpmn20.xml";

    private ProcessEngine processEngine;
    private RepositoryService repositoryService;
    private RuntimeService runtimeService;

    protected final Provider<PersistenceProviderJPA> jpaContextProvider = Jdp.getProvider(PersistenceProviderJPA.class);
    protected final Provider<RequestContext> contextProvider = Jdp.getProvider(RequestContext.class);
    protected final T9tServerConfiguration config = Jdp.getRequired(T9tServerConfiguration.class);

    @Override
    public void init(Map<String, IWorkflowStep> businessProcessBeans) {
        LOGGER.info("Initializing Activiti BPMN 2 engine");

        StandaloneInMemProcessEngineConfiguration configuration = (StandaloneInMemProcessEngineConfiguration)
                ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();

        configuration.setCreateDiagramOnDeploy(true);
        configuration.setJpaEntityManagerFactory(jpaContextProvider.get().getEntityManager().getEntityManagerFactory());
        configuration.setJdbcDriver(config.getDatabaseConfiguration().getJdbcDriverClass());
        configuration.setJdbcUrl(config.getDatabaseConfiguration().getJdbcConnectString());
        configuration.setJdbcUsername(config.getDatabaseConfiguration().getUsername());
        configuration.setJdbcPassword(config.getDatabaseConfiguration().getPassword());
        configuration.setJpaHandleTransaction(false);
        configuration.setJpaCloseEntityManager(false);
        configuration.setDatabaseSchemaUpdate("true");

        Map<Object, Object> beans = new HashMap<>();
        businessProcessBeans.forEach((key, value) -> { beans.put(key, value); });
        configuration.setBeans(beans);

        T9tActivityBehaviorFactory activityBehaviorFactory = new T9tActivityBehaviorFactory();
        activityBehaviorFactory.setExpressionManager(new ExpressionManager(beans));
        configuration.setActivityBehaviorFactory(activityBehaviorFactory);

        processEngine = configuration.buildProcessEngine();
        repositoryService = processEngine.getRepositoryService();
        runtimeService = processEngine.getRuntimeService();
        LOGGER.info("Activiti BPMN 2 service has been initialized");
    }

    @Override
    public void deployProcessInternal(DeployResourceBean deployInternalBean) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        long prevDeploymentCount = repositoryService.createDeploymentQuery().deploymentName(deployInternalBean.getDeploymentName()).count();

        DeploymentBuilder builder = repositoryService.createDeployment();
        builder.name(deployInternalBean.getDeploymentName());
        builder.enableDuplicateFiltering();
        builder.addInputStream(CONTENT_RESOURCE, deployInternalBean.getInputStream());

        Deployment deployment = builder.deploy();

        // plausibility check on the number of process definitions deployed
        // this is BEST PRACTISE to only deploy 1 process definition per file
        // maybe we want to relax this constraint in the future ;)
        long noOfDeployedProcessDefinitions = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).count();
        if (noOfDeployedProcessDefinitions > 1) {
            repositoryService.deleteDeployment(deployment.getId());
            LOGGER.error("Attempted to deploy more than 1 process definition in 1 single file (found {} process definitions).", noOfDeployedProcessDefinitions);
            throw new T9tBPMException(T9tBPMException.BPM_DEPLOYMENT_MULTI_PROCESS_DEFINITION);
        }
    }

    @Override
    public String findDeploymentByProcessDefinitionId(String processDefinitionId) {
        String deploymentId = null;
        RepositoryService repositoryService = processEngine.getRepositoryService();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionId).latestVersion().singleResult();
        if (processDefinition == null) {
            return deploymentId;
        }

        deploymentId = repositoryService.createDeploymentQuery().deploymentId(processDefinition.getDeploymentId()).singleResult().getId();
        return deploymentId;
    }

    @Override
    public InputStream getProcessDefinitionDiagram(String deploymentId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        return repositoryService.getResourceAsStream(deploymentId, processDefinition.getDiagramResourceName());
    }

    @Override
    public InputStream getProcessDefinitionContent(String deploymentId) {
        return processEngine.getRepositoryService().getResourceAsStream(deploymentId, CONTENT_RESOURCE);
    }

    @Override
    public void startProcessInstanceByKey(String internalProcessId, Map<String, Object> processParams) {

        try {
            ProcessInstance instance = runtimeService.startProcessInstanceByKey(internalProcessId, processParams);
        } catch (ActivitiException ex) {
            LOGGER.error("Root exception", ex);

            Throwable currentException = ex;
            while (currentException.getCause() != null) {
                currentException = currentException.getCause();

                // TODO: handle optimistic and pessimistic locking somewhere!!!???

                // the first cause is usually an activity expression language exception which is a useless wrapper
                if (currentException instanceof ELException) {
                    continue;
                } else if (currentException instanceof T9tException) {
                    T9tException exception = (T9tException) currentException;
                    throw new T9tBPMException(exception.getErrorCode(), currentException.getMessage());
                } else {
                    throw new T9tBPMException(T9tException.GENERAL_EXCEPTION, currentException.getMessage());
                }
            }

            throw new T9tBPMException(T9tBPMException.BPM_EXECUTE_PROCESS_ERROR);
        }
    }
}
