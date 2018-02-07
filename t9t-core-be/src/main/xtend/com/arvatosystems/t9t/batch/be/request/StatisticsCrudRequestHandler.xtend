package com.arvatosystems.t9t.batch.be.request;

import com.arvatosystems.t9t.base.api.ServiceResponse
import com.arvatosystems.t9t.base.be.impl.AbstractCrudSurrogateKeyBERequestHandler
import com.arvatosystems.t9t.base.entities.WriteTracking
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.batch.StatisticsDTO
import com.arvatosystems.t9t.batch.StatisticsRef
import com.arvatosystems.t9t.batch.request.StatisticsCrudRequest
import de.jpaw.annotations.AddLogger
import de.jpaw.dp.Inject
import com.arvatosystems.t9t.batch.services.IStatisticsResolver

@AddLogger
public class StatisticsCrudRequestHandler extends AbstractCrudSurrogateKeyBERequestHandler<StatisticsRef, StatisticsDTO, WriteTracking, StatisticsCrudRequest> {
    @Inject IStatisticsResolver resolver

    override ServiceResponse execute(RequestContext ctx, StatisticsCrudRequest crudRequest) {
        return execute(ctx, crudRequest, resolver)
    }
}
