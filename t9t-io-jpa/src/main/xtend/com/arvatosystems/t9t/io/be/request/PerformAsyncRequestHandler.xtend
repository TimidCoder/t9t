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
package com.arvatosystems.t9t.io.be.request

import com.arvatosystems.t9t.base.services.AbstractRequestHandler
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.io.AsyncChannelKey
import com.arvatosystems.t9t.io.jpa.persistence.IAsyncChannelEntityResolver
import com.arvatosystems.t9t.io.request.PerformAsyncRequest
import com.arvatosystems.t9t.out.services.IAsyncTransmitter
import de.jpaw.annotations.AddLogger
import de.jpaw.dp.Inject

@AddLogger
class PerformAsyncRequestHandler extends AbstractRequestHandler<PerformAsyncRequest> {
    @Inject IAsyncChannelEntityResolver channelResolver
    @Inject IAsyncTransmitter asyncTransmitter

    override execute(RequestContext ctx, PerformAsyncRequest rq) {
        channelResolver.getEntityData(new AsyncChannelKey(rq.asyncChannelId), true)  // just a check to see if the channel has been configured
        asyncTransmitter.transmitMessage(rq.asyncChannelId, rq.payload,
            rq.ref ?: ctx.getRequestRef, rq.refType ?: "REQ", rq.refIdentifier ?: ctx.userId
        )
        return ok
    }
}
