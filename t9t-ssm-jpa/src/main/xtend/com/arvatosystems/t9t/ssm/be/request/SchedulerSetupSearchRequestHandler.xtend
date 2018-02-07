package com.arvatosystems.t9t.ssm.be.request;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion
import com.arvatosystems.t9t.base.search.ReadAllResponse
import com.arvatosystems.t9t.base.services.AbstractSearchRequestHandler
import com.arvatosystems.t9t.core.CannedRequestDTO
import com.arvatosystems.t9t.ssm.SchedulerSetupDTO
import com.arvatosystems.t9t.ssm.jpa.mapping.ISchedulerSetupDTOMapper
import com.arvatosystems.t9t.ssm.jpa.persistence.ISchedulerSetupEntityResolver
import com.arvatosystems.t9t.ssm.request.SchedulerSetupSearchRequest
import de.jpaw.annotations.AddLogger
import de.jpaw.dp.Inject

@AddLogger
public class SchedulerSetupSearchRequestHandler extends AbstractSearchRequestHandler<SchedulerSetupSearchRequest> {
    @Inject protected ISchedulerSetupEntityResolver resolver

    @Inject protected ISchedulerSetupDTOMapper mapper

    override public ReadAllResponse<SchedulerSetupDTO, FullTrackingWithVersion> execute(SchedulerSetupSearchRequest request) throws Exception {
        val response = mapper.createReadAllResponse(resolver.search(request, null), request.getSearchOutputTarget());
        if (Boolean.TRUE == request.suppressResponseParameters) {
            // null out explicit request parameters
            for (dwt : response.dataList)
                if (dwt.data.request !== null)
                    ((dwt.data.request) as CannedRequestDTO).request = null
        }
        return response
    }
}
