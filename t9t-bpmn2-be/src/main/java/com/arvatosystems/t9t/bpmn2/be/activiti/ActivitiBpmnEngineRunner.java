package com.arvatosystems.t9t.bpmn2.be.activiti;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.MessagingUtil;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.bpmn.IBPMObjectFactory;
import com.arvatosystems.t9t.bpmn.ProcessDefinitionDTO;
import com.arvatosystems.t9t.bpmn.ProcessExecutionStatusDTO;
import com.arvatosystems.t9t.bpmn.T9tBPMException;
import com.arvatosystems.t9t.bpmn.WorkflowReturnCode;
import com.arvatosystems.t9t.bpmn.jpa.entities.ProcessExecStatusEntity;
import com.arvatosystems.t9t.bpmn.jpa.persistence.IProcessExecStatusEntityResolver;
import com.arvatosystems.t9t.bpmn.pojo.ProcessOutput;
import com.arvatosystems.t9t.bpmn.services.IBPMService;
import com.arvatosystems.t9t.bpmn.services.IBpmnEngineRunner;

import de.jpaw.bonaparte.jpa.refs.PersistenceProviderJPA;
import de.jpaw.dp.Jdp;
import de.jpaw.dp.Named;
import de.jpaw.dp.Provider;
import de.jpaw.dp.Singleton;

@Named("activiti")
@Singleton
public class ActivitiBpmnEngineRunner implements IBpmnEngineRunner {

    public static final Logger LOGGER = LoggerFactory.getLogger(ActivitiBpmnEngineRunner.class);
    private IBPMService bpmService = Jdp.getRequired(IBPMService.class);
    private IProcessExecStatusEntityResolver statusResolver = Jdp.getRequired(IProcessExecStatusEntityResolver.class);
    protected final Provider<PersistenceProviderJPA> jpaContextProvider = Jdp.getProvider(PersistenceProviderJPA.class);

    @Override
    public boolean run(RequestContext ctx, Long statusRef, ProcessDefinitionDTO pd, IBPMObjectFactory<?> factory, Long lockObjectRef, boolean jvmLockAcquired) {

        // ProcessExecStatusEntity status = statusResolver.find(statusRef);

        // if (status != null) {
        Object workflowObject = factory.read(statusRef, lockObjectRef, jvmLockAcquired);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(IBPMService.BPMN_WORKFLOW_DATA, workflowObject);

        LOGGER.info("Before BPMN. Transaction is {}", jpaContextProvider.get().getEntityManager().getTransaction().isActive());

        ProcessOutput processOutput = bpmService.executeProcess(pd.getProcessDefinitionId(), parameters);

        LOGGER.info("After BPMN. Transaction is {}", jpaContextProvider.get().getEntityManager().getTransaction().isActive());

        WorkflowReturnCode returnCode = (WorkflowReturnCode) processOutput.get("workflowStatus");

        switch (returnCode) {
        case ERROR:
            // dealWithError(status, processOutput.get("returnCode"), processOutput);
            break;
        case COMMIT_RESTART:
            break;
        case DONE:
            // statusResolver.getEntityManager().remove(status);
            break;
        case PROCEED_NEXT:
            break;
        case YIELD:
            break;
        case YIELD_NEXT:
            break;
        default:
            break;
        }
        // }
        return false;
    }

    private void dealWithError(ProcessExecStatusEntity statusEntity, Object returnCode, ProcessOutput processOutput) {
        Integer derivedReturnCode = null;
        if (((returnCode == null) || (!(returnCode instanceof Integer)))) {
            derivedReturnCode = Integer.valueOf(T9tBPMException.BPM_NO_ERROR);
        } else {
            derivedReturnCode = ((Integer) returnCode);
        }
        statusEntity.setReturnCode(derivedReturnCode);
        statusEntity.setErrorDetails(MessagingUtil.truncField(processOutput.get("errorDetails"), ProcessExecutionStatusDTO.meta$$errorDetails.getLength()));
    }

}
