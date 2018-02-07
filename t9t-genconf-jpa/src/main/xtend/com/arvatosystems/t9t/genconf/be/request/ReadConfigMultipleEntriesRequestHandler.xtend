package com.arvatosystems.t9t.genconf.be.request;

import com.arvatosystems.t9t.base.T9tConstants
import com.arvatosystems.t9t.base.services.AbstractRequestHandler
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.genconf.jpa.mapping.IConfigDTOMapper
import com.arvatosystems.t9t.genconf.jpa.persistence.IConfigEntityResolver
import com.arvatosystems.t9t.genconf.request.ReadConfigMultipleEntriesRequest
import com.arvatosystems.t9t.genconf.request.ReadConfigMultipleEntriesResponse
import de.jpaw.dp.Inject

public class ReadConfigMultipleEntriesRequestHandler extends AbstractRequestHandler<ReadConfigMultipleEntriesRequest> {
    @Inject IConfigEntityResolver resolver
    @Inject IConfigDTOMapper mapper

    override boolean isReadOnly(ReadConfigMultipleEntriesRequest params) {
        return true;
    }

    override ReadConfigMultipleEntriesResponse execute(RequestContext ctx, ReadConfigMultipleEntriesRequest request) throws Exception {

        return new ReadConfigMultipleEntriesResponse => [
            entries = mapper.mapListToDto((resolver.findByGroup(true, (if (request.readGlobalTenant) T9tConstants.GLOBAL_TENANT_REF42 else ctx.tenantRef), request.getConfigGroup())))
        ]
    }
}
