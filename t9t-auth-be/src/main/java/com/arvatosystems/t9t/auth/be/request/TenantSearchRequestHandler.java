package com.arvatosystems.t9t.auth.be.request;

import com.arvatosystems.t9t.auth.TenantDTO;
import com.arvatosystems.t9t.auth.request.TenantSearchRequest;
import com.arvatosystems.t9t.auth.services.ITenantResolver;
import com.arvatosystems.t9t.base.be.impl.AbstractSearchBERequestHandler;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.search.ReadAllResponse;

import de.jpaw.dp.Jdp;

public class TenantSearchRequestHandler extends AbstractSearchBERequestHandler<TenantDTO, FullTrackingWithVersion, TenantSearchRequest> {

    // @Inject
    protected final ITenantResolver resolver = Jdp.getRequired(ITenantResolver.class);

    @Override
    public ReadAllResponse<TenantDTO, FullTrackingWithVersion> execute(TenantSearchRequest request) {
        return execute(resolver.query(
                request.getLimit(),
                request.getOffset(),
                request.getSearchFilter(),
                request.getSortColumns()),
                request.getSearchOutputTarget());
    }
}
