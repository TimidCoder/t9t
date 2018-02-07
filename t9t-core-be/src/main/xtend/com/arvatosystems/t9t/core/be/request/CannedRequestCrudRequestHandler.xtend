package com.arvatosystems.t9t.core.be.request

import com.arvatosystems.t9t.base.api.ServiceResponse
import com.arvatosystems.t9t.base.be.impl.AbstractCrudSurrogateKeyBERequestHandler
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.core.CannedRequestDTO
import com.arvatosystems.t9t.core.CannedRequestRef
import com.arvatosystems.t9t.core.request.CannedRequestCrudRequest
import com.arvatosystems.t9t.core.services.ICannedRequestResolver
import de.jpaw.annotations.AddLogger
import de.jpaw.dp.Inject

@AddLogger
class CannedRequestCrudRequestHandler extends AbstractCrudSurrogateKeyBERequestHandler<CannedRequestRef, CannedRequestDTO, FullTrackingWithVersion, CannedRequestCrudRequest> {
    @Inject CannedRequestParameterEvaluator evaluator
    @Inject ICannedRequestResolver          resolver

    override ServiceResponse execute(RequestContext ctx, CannedRequestCrudRequest crudRequest) {
        val CannedRequestDTO dto = crudRequest.data

        switch (crudRequest.crud) {
            case CREATE, case UPDATE, case MERGE: {
                evaluator.processDTO(dto)
            }
            default: {
            }
        }
        val response = execute(ctx, crudRequest, resolver)
        if (Boolean.TRUE == crudRequest.suppressResponseParameters && response.data !== null)
            response.data.request = null
        return response
    }
}
