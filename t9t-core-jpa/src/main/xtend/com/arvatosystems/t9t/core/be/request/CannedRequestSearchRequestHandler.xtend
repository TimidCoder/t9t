package com.arvatosystems.t9t.core.be.request;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion
import com.arvatosystems.t9t.base.search.ReadAllResponse
import com.arvatosystems.t9t.base.services.AbstractSearchRequestHandler
import com.arvatosystems.t9t.core.CannedRequestDTO
import com.arvatosystems.t9t.core.jpa.mapping.ICannedRequestDTOMapper
import com.arvatosystems.t9t.core.jpa.persistence.ICannedRequestEntityResolver
import com.arvatosystems.t9t.core.request.CannedRequestSearchRequest
import de.jpaw.annotations.AddLogger
import de.jpaw.dp.Inject

@AddLogger
public class CannedRequestSearchRequestHandler extends AbstractSearchRequestHandler<CannedRequestSearchRequest> {
    @Inject protected final ICannedRequestEntityResolver resolver

    @Inject protected final ICannedRequestDTOMapper mapper

    override public ReadAllResponse<CannedRequestDTO, FullTrackingWithVersion> execute(CannedRequestSearchRequest request) throws Exception {
        val response = mapper.createReadAllResponse(resolver.search(request, null), request.getSearchOutputTarget());
        if (Boolean.TRUE == request.suppressResponseParameters) {
              // null out explicit request parameters
              for (dwt : response.dataList)
                  dwt.data.request = null
        }
        return response
    }
}
