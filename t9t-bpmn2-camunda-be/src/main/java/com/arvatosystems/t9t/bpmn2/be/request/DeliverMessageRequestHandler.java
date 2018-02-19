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
package com.arvatosystems.t9t.bpmn2.be.request;

import static com.arvatosystems.t9t.bpmn2.be.camunda.utils.IdentifierConverter.t9tTenantRefToBPMNTenantId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.MismatchingMessageCorrelationException;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.MessageCorrelationBuilder;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.runtime.MessageCorrelationResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.bpmn2.request.DeliverMessageRequest;

import de.jpaw.dp.Jdp;

public class DeliverMessageRequestHandler extends AbstractBPMNRequestHandler<DeliverMessageRequest> {

    private final RuntimeService runtimeService = Jdp.getRequired(RuntimeService.class);

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliverMessageRequestHandler.class);

    @Override
    protected ServiceResponse executeInWorkflowContext(RequestContext requestContext, DeliverMessageRequest request) throws Exception {
        LOGGER.debug("Deliver message '{}' with business key '{}' - Payload: {}", request.getMessageName(), request.getBusinessKey(), request.getVariables());

        MessageCorrelationBuilder messageBuilder = runtimeService.createMessageCorrelation(request.getMessageName())
                                                                 .tenantId(t9tTenantRefToBPMNTenantId(requestContext.getTenantRef()));

        if (request.getBusinessKey() != null) {
            messageBuilder = messageBuilder.processInstanceBusinessKey(request.getBusinessKey());
        }

        final Map<String, Object> variables = new HashMap<>();
        if (request.getVariables() != null) {
            variables.putAll(request.getVariables());
        }
        messageBuilder = messageBuilder.setVariables(variables);

        if (request.getBusinessKey() != null) {
            try {
                // Send to exact one subscriber.
                LOGGER.debug("Due to existing business key, delivery message to single subscriber");
                messageBuilder.correlate(); // NOTE: to allow multiple t9t cluster nodes to receive the event, we could also use correlateExclusively - but this will cause pessimistic locks!
            } catch (MismatchingMessageCorrelationException e) {
                // There is no or more than one subscriber. This is an issue, since we are using a business key to
                // directly address some participant.

                // FIXME: Raise appropriate error !
                throw new IllegalStateException("Could not deliver message", e);
            }
        } else {
            // Since we do not directly address some participant, just perform some kind of broadcast with fire and
            // forget. We can not check anyway...
            LOGGER.debug("Due to no existing business key, deliver to all (or no) subscriber");
            final List<MessageCorrelationResult> result = messageBuilder.correlateAllWithResult();

            if (LOGGER.isDebugEnabled()) {
                if (result.isEmpty()) {
                    LOGGER.debug("No subscriber available - message is dropped");
                } else {
                    for (MessageCorrelationResult correlation : result) {
                        LOGGER.debug("Process instance id '{}' (business key '{}') {}",
                                correlation.getProcessInstance()
                                           .getId(),
                                correlation.getProcessInstance()
                                           .getBusinessKey(),
                                correlation.getResultType() == MessageCorrelationResultType.ProcessDefinition ? "started by message" : "received message");
                    }
                }
            }
        }

        return ok();
    }

}
