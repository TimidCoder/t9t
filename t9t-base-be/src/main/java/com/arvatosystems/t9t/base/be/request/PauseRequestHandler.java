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
package com.arvatosystems.t9t.base.be.request;

import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.request.PauseRequest;
import com.arvatosystems.t9t.base.request.PauseResponse;
import com.arvatosystems.t9t.base.services.AbstractReadOnlyRequestHandler;


/**
 * The default implementation responsible for handling all incoming requests of type {@link PauseRequest}.
 */
public class PauseRequestHandler extends AbstractReadOnlyRequestHandler<PauseRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PauseRequestHandler.class);

    @Override
    public PauseResponse execute(PauseRequest pingRequest) {
//        LOGGER.info("Ping request handler called for {}", pingRequest);

        PauseResponse response = new PauseResponse();
        response.setReturnCode(0);
        response.setPingId(pingRequest.getPingId());
        response.setWhenExecuted(new Instant());

        Integer delayInMillis = pingRequest.getDelayInMillis();
        if ((delayInMillis != null) && (delayInMillis.intValue() > 0)) {
            try {
                Thread.sleep(delayInMillis);
            } catch (InterruptedException e) {
                LOGGER.warn("A PauseRequest with delay {} milliseconds has been interrupted and sent the response earlier.", delayInMillis);
            }
        }

        response.setWhenFinished(new Instant());
        return response;
    }
}
