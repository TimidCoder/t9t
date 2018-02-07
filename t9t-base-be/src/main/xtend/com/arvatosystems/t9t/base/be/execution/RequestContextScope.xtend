package com.arvatosystems.t9t.base.be.execution

import com.arvatosystems.t9t.base.request.ProcessStatusDTO
import com.arvatosystems.t9t.base.request.ProcessStatusRequest
import com.arvatosystems.t9t.base.services.RequestContext
import de.jpaw.annotations.AddLogger
import de.jpaw.dp.JdpThreadLocalStrict
import de.jpaw.dp.Singleton
import java.util.ArrayList
import java.util.List
import org.joda.time.Instant

/**
 * An extension of the ThreadLocal scope
 */
@Singleton
@AddLogger
class RequestContextScope extends JdpThreadLocalStrict<RequestContext> {

    override void set(RequestContext ctx) {
        // debug!
        if (instances.get(threadId()) !== null) {
            val String msg = "RequestContext already set - coding problem - probably you called the wrong Exector.executeSynchronous() method"
            LOGGER.error(msg)
            throw new RuntimeException(msg)
        }
        super.set(ctx) // LOGGER.info("XXXXXXXXXXXXXXXX SET");
    }

    def List<ProcessStatusDTO> getProcessStatus(ProcessStatusRequest filters, Instant myExecutionStart) {
        val refAge = myExecutionStart.millis
        val result = new ArrayList<ProcessStatusDTO>(30)
        instances.forEach[ threadId, it |
            val thisAge = refAge - executionStart.millis
            if (thisAge >= filters.minAgeInMs &&
               (filters.tenantId === null || filters.tenantId == tenantId) &&
               (filters.userId   === null || filters.userId == userId)) {
                val hdr                 = internalHeaderParameters
                val dto                 = new ProcessStatusDTO
                dto.threadId            = threadId
                dto.ageInMs             = thisAge
                dto.tenantId            = tenantId
                dto.userId              = userId
                dto.sessionRef          = hdr.jwtInfo.sessionRef
                dto.processRef          = requestRef
                dto.processStartedAt    = executionStart
                dto.pqon                = hdr.requestParameterPqon
                dto.invokingProcessRef  = hdr.requestHeader?.invokingProcessRef
                dto.progressCounter     = progressCounter
                if (statusText !== null) {
                    dto.statusText      = if (statusText.length <= 512) statusText else statusText.substring(0, 512)
                }
                result.add(dto)
            }
        ]
        return result
    }
}
