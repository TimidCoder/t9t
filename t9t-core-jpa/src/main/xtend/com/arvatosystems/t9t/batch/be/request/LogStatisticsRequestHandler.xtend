package com.arvatosystems.t9t.batch.be.request

import com.arvatosystems.t9t.base.services.AbstractRequestHandler
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.batch.jpa.mapping.IStatisticsDTOMapper
import com.arvatosystems.t9t.batch.jpa.persistence.IStatisticsEntityResolver
import com.arvatosystems.t9t.batch.request.LogStatisticsRequest
import de.jpaw.dp.Inject

class LogStatisticsRequestHandler extends AbstractRequestHandler<LogStatisticsRequest> {
    @Inject IStatisticsEntityResolver entityResolver
    @Inject IStatisticsDTOMapper dtoMapper

    override execute(RequestContext ctx, LogStatisticsRequest rq) {
        val entity = dtoMapper.mapToEntity(rq.statistics, false)
        entity.objectRef = entityResolver.createNewPrimaryKey;
        entityResolver.save(entity)
        return ok
    }
}
