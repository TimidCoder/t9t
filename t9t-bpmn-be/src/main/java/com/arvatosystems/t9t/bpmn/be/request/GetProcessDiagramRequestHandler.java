package com.arvatosystems.t9t.bpmn.be.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.IRequestHandler;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.bpmn.T9tBPMException;
import com.arvatosystems.t9t.bpmn.request.GetProcessDiagramRequest;
import com.arvatosystems.t9t.bpmn.request.GetProcessDiagramResponse;
import com.arvatosystems.t9t.bpmn.services.IBPMService;

import de.jpaw.dp.Jdp;
import de.jpaw.util.ByteArray;

/**
 * Implementation {@linkplain IRequestHandler} which handles {@linkplain GetProcessDiagramRequest}.
 * @author LIEE001
 */

public class GetProcessDiagramRequestHandler extends AbstractRequestHandler<GetProcessDiagramRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeployProcessRequestHandler.class);
    private final IBPMService bpmService = Jdp.getOptional(IBPMService.class);

    @Override
    public GetProcessDiagramResponse execute(RequestContext requestCtx, final GetProcessDiagramRequest request) throws Exception {
        GetProcessDiagramResponse response = new GetProcessDiagramResponse();

        if (bpmService == null) {
            LOGGER.error("Fail to lookup implementation for IBpmService. Please check your deployment package");
            throw new T9tBPMException(T9tBPMException.BPM_NO_BPMN_ENGINE);
        }

        try {
            byte[] diagramInBytes = bpmService.getProcessDiagram(request.getProcessDefinitionRef());

            response.setDiagram(new ByteArray(diagramInBytes));
            response.setReturnCode(0);
            return response;
        } catch (T9tBPMException ex) {
            LOGGER.error(String.format("Failed to get process diagrams (tenantId: %s, userId:%s, processDefinitionRef: %s).",
                    requestCtx.tenantId, requestCtx.userId, request.getProcessDefinitionRef()), ex);

            throw ex;
        }
    }

}
