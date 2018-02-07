package com.arvatosystems.t9t.auth.be.request;

import com.arvatosystems.t9t.auth.ApiKeyDTO;
import com.arvatosystems.t9t.auth.request.ApiKeySearchRequest;
import com.arvatosystems.t9t.auth.services.IApiKeyResolver;
import com.arvatosystems.t9t.base.be.impl.AbstractSearchBERequestHandler;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.search.ReadAllResponse;

import de.jpaw.dp.Jdp;

public class ApiKeySearchRequestHandler extends AbstractSearchBERequestHandler<ApiKeyDTO, FullTrackingWithVersion, ApiKeySearchRequest> {

    // @Inject
    protected final IApiKeyResolver resolver = Jdp.getRequired(IApiKeyResolver.class);

    @Override
    public ReadAllResponse<ApiKeyDTO, FullTrackingWithVersion> execute(ApiKeySearchRequest request) {
        return execute(resolver.query(
                request.getLimit(),
                request.getOffset(),
                request.getSearchFilter(),
                request.getSortColumns()),
                request.getSearchOutputTarget());
    }
}
