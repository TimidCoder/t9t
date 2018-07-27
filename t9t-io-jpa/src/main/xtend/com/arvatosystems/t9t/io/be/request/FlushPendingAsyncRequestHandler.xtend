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

import com.arvatosystems.t9t.base.output.ExportStatusEnum
import com.arvatosystems.t9t.base.output.OutputSessionParameters
import com.arvatosystems.t9t.base.services.AbstractRequestHandler
import com.arvatosystems.t9t.base.services.IOutputSession
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.io.jpa.entities.AsyncMessageEntity
import com.arvatosystems.t9t.io.jpa.persistence.IAsyncMessageEntityResolver
import com.arvatosystems.t9t.io.request.FlushPendingAsyncRequest
import com.arvatosystems.t9t.io.request.FlushPendingAsyncResponse
import com.arvatosystems.t9t.out.services.IAsyncQueue
import de.jpaw.annotations.AddLogger
import de.jpaw.dp.Inject
import de.jpaw.dp.Provider

@AddLogger
class FlushPendingAsyncRequestHandler extends AbstractRequestHandler<FlushPendingAsyncRequest> {
    @Inject IAsyncMessageEntityResolver messageResolver
    @Inject IAsyncQueue queueImpl;
    @Inject Provider<IOutputSession> outputSessionProvider

    override FlushPendingAsyncResponse execute(RequestContext ctx, FlushPendingAsyncRequest rq) {
        if (rq.markAsDone) {
            queueImpl.clearQueue();  // clear any current entries from the in-memory
        }
        if (!rq.exportToFile) {
            val num = process(ctx, null, rq)
            LOGGER.debug("Retrieved {} messages for channel {}", num, rq.onlyChannelId ?: "(all)");
            // create response
            val resp                     = new FlushPendingAsyncResponse
            resp.numberOfRecords  = num
            return resp
        }
        val os = outputSessionProvider.get
        val mySinkRef = os.open(new OutputSessionParameters => [
            dataSinkId = rq.dataSinkId ?: "asyncSink"
        ])
        val num = process(ctx, os, rq)
        val myFileName = os.fileOrQueueName
        os.close
        LOGGER.debug("Retrieved {} messages for channel {}", num, rq.onlyChannelId ?: "(all)");

        // create response
        val resp                     = new FlushPendingAsyncResponse
        resp.numberOfRecords         = num
        resp.sinkRef                 = mySinkRef
        resp.filename                = myFileName
        return resp
    }

    def int process(RequestContext ctx, IOutputSession os, FlushPendingAsyncRequest rq) {
        val extraCondition = if (rq.onlyChannelId !== null) " AND m.asyncChannelId = :channel";
        val query = messageResolver.entityManager.createQuery('''
            SELECT m FROM AsyncMessageEntity m WHERE m.status != null AND m.tenantRef = :tenantRef«extraCondition»
             ORDER BY m.objectRef''', AsyncMessageEntity)
        query.setParameter("tenantRef", messageResolver.sharedTenantRef)
        if (rq.onlyChannelId !== null)
            query.setParameter("channel", rq.onlyChannelId);
        val results = query.resultList
        for (m: results) {
            os?.store(m.payload)
            if (rq.markAsDone)
                m.status = ExportStatusEnum.RESPONSE_OK
        }
        return results.size
    }
}
