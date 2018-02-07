package com.arvatosystems.t9t.core.be.request

import com.arvatosystems.t9t.base.T9tException
import com.arvatosystems.t9t.base.api.RequestParameters
import com.arvatosystems.t9t.base.api.ServiceResponse
import com.arvatosystems.t9t.base.auth.PermissionType
import com.arvatosystems.t9t.base.services.AbstractRequestHandler
import com.arvatosystems.t9t.base.services.IExecutor
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.core.CannedRequestDTO
import com.arvatosystems.t9t.core.request.ExecuteCannedRequest
import com.arvatosystems.t9t.core.services.ICannedRequestResolver
import com.arvatosystems.t9t.server.services.IAuthorize
import de.jpaw.annotations.AddLogger
import de.jpaw.bonaparte.pojos.api.OperationType
import de.jpaw.dp.Inject

@AddLogger
class ExecuteCannedRequestHandler extends AbstractRequestHandler<ExecuteCannedRequest> {
    @Inject protected CannedRequestParameterEvaluator evaluator
    @Inject protected ICannedRequestResolver          resolver
    @Inject protected IExecutor                       executor
    @Inject protected IAuthorize                      authorizator

    def ServiceResponse checkAuthorizationAndExecuteStrict(RequestContext ctx, RequestParameters rq, boolean providedOwnRequest) {
        val permissions = authorizator.getPermissions(ctx.internalHeaderParameters.jwtInfo, PermissionType.BACKEND, rq.ret$PQON)
        LOGGER.debug("Backend execution permissions checked for request {}, got {}", rq.ret$PQON, permissions)
        var boolean forbidden = !permissions.contains(OperationType.EXECUTE)
        if (providedOwnRequest && !permissions.contains(OperationType.CREATE))
            forbidden = true;
        if (forbidden) {
            return new ServiceResponse => [
                returnCode          = T9tException.NOT_AUTHORIZED
                errorDetails        = OperationType.EXECUTE.name
            ]
        }
        // access granted!
        return executor.executeSynchronous(rq)
    }

    def ServiceResponse checkAuthorizationAndExecute(RequestContext ctx, RequestParameters rq, boolean providedOwnRequest) {
        return executor.executeSynchronous(rq)
    }
    override ServiceResponse execute(RequestContext ctx, ExecuteCannedRequest rq) {
        val ref = rq.requestRef
        if (ref instanceof CannedRequestDTO) {
            val dto = ref
            // no read required, maybe a composition of the parameters from JSON
            evaluator.processDTO(dto)
            LOGGER.info("Executing provided canned request of ID {} for request {}", dto.requestId, dto.request.ret$PQON)
            return checkAuthorizationAndExecute(ctx, dto.request, true)
        } else {
            // resolve the DTO from DB
            val dto = resolver.getDTO(ref)
            LOGGER.info("Executing resolved canned request of ID {} for request {}", dto.requestId, dto.request.ret$PQON)
            return checkAuthorizationAndExecute(ctx, dto.request, false)
        }
    }
}
