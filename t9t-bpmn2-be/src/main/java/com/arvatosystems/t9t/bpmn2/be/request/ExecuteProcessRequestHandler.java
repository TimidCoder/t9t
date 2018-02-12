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
