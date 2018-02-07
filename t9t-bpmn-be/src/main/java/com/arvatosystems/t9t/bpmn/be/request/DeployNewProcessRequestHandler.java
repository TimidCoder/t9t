package com.arvatosystems.t9t.bpmn.be.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.IRequestHandler;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.bpmn.ProcessDefinitionDTO;
import com.arvatosystems.t9t.bpmn.T9tBPMException;
import com.arvatosystems.t9t.bpmn.request.DeployNewProcessRequest;
import com.arvatosystems.t9t.bpmn.request.DeployNewProcessResponse;
import com.arvatosystems.t9t.bpmn.services.IBPMService;

import de.jpaw.dp.Jdp;

/**
 * Implementation {@linkplain IRequestHandler} which handles {@linkplain DeployNewProcessRequest}.
 * @author LIEE001
 */
public class DeployNewProcessRequestHandler extends AbstractRequestHandler<DeployNewProcessRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeployNewProcessRequestHandler.class);
    private final IBPMService bpmService = Jdp.getOptional(IBPMService.class);

    @Override
    public DeployNewProcessResponse execute(RequestContext requestCtx,final DeployNewProcessRequest request) throws Exception {
        DeployNewProcessResponse response = new DeployNewProcessResponse();
        if (bpmService == null) {
            LOGGER.error("Fail to lookup implementation for IBpmService. Please check your deployment package");
            throw new T9tBPMException(T9tBPMException.BPM_NO_BPMN_ENGINE);
        }

        try {
            ProcessDefinitionDTO processDefinitionDTO = bpmService.deployNewProcess(request.getDeploymentComment(),
                    request.getContent().getBytes());

            response.setProcessDefinition(processDefinitionDTO);
            response.setReturnCode(0);
            return response;
        } catch (T9tBPMException ex) {
            // T9tBPMException signifies a technical exception
            LOGGER.error("Failed to deploy new process (tenantId: {}, userId: {}).", requestCtx.tenantId, requestCtx.userId, ex.getMessage());
            throw ex;
        }
    }
}
