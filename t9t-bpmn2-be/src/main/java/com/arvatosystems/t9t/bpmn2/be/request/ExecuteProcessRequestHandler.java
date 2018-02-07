//package com.arvatosystems.t9t.bpmn2.be.request;
//
//import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
//import com.arvatosystems.t9t.base.services.IRequestHandler;
//import com.arvatosystems.t9t.base.services.RequestContext;
//import com.arvatosystems.t9t.bpmn.services.IBPMService;
//import com.arvatosystems.t9t.bpmn2.be.ProcessOutput;
//import com.arvatosystems.t9t.bpmn2.request.ExecuteProcessRequest;
//import com.arvatosystems.t9t.bpmn2.request.ExecuteProcessResponse;
//
//import de.jpaw.dp.Jdp;
//
///**
// * Implementation {@linkplain IRequestHandler} which handles {@linkplain ExecuteProcessRequest}.
// * @author LIEE001
// */
//public class ExecuteProcessRequestHandler extends AbstractRequestHandler<ExecuteProcessRequest> {
//
//    private final IBPMService bpmService = Jdp.getRequired(IBPMService.class);
//
//    @Override
//    public ExecuteProcessResponse execute(RequestContext requestCtx, final ExecuteProcessRequest request) throws Exception {
//        ProcessOutput processOutput = bpmService.executeProcess(request.getProcessKey(), request.getParams());
//        ExecuteProcessResponse response = new ExecuteProcessResponse();
//        response.setOutputs(processOutput.getAll());
//        response.setReturnCode(0);
//        return response;
//    }
//}
