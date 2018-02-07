package com.arvatosystems.t9t.batch.be.request;

import com.arvatosystems.t9t.base.api.ServiceResponse
import com.arvatosystems.t9t.base.be.impl.AbstractCrudSurrogateKeyBERequestHandler
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.batch.SliceTrackingDTO
import com.arvatosystems.t9t.batch.SliceTrackingRef
import com.arvatosystems.t9t.batch.request.SliceTrackingCrudRequest
import com.arvatosystems.t9t.batch.services.ISliceTrackingResolver
import de.jpaw.annotations.AddLogger
import de.jpaw.dp.Inject

@AddLogger
public class SliceTrackingCrudRequestHandler extends AbstractCrudSurrogateKeyBERequestHandler<SliceTrackingRef, SliceTrackingDTO, FullTrackingWithVersion, SliceTrackingCrudRequest> {
    @Inject ISliceTrackingResolver resolver

    override ServiceResponse execute(RequestContext ctx, SliceTrackingCrudRequest crudRequest) {
        return execute(ctx, crudRequest, resolver)
    }
}
