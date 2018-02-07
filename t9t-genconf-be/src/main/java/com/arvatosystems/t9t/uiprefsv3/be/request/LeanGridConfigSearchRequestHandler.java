package com.arvatosystems.t9t.uiprefsv3.be.request;

import com.arvatosystems.t9t.base.be.impl.AbstractSearchBERequestHandler;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.search.ReadAllResponse;
import com.arvatosystems.t9t.uiprefsv3.LeanGridConfigDTO;
import com.arvatosystems.t9t.uiprefsv3.request.LeanGridConfigSearchRequest;
import com.arvatosystems.t9t.uiprefsv3.services.ILeanGridConfigResolver;

import de.jpaw.dp.Jdp;

public class LeanGridConfigSearchRequestHandler extends AbstractSearchBERequestHandler<LeanGridConfigDTO, FullTrackingWithVersion, LeanGridConfigSearchRequest> {

    // @Inject
    protected final ILeanGridConfigResolver resolver = Jdp.getRequired(ILeanGridConfigResolver.class);

    @Override
    public ReadAllResponse<LeanGridConfigDTO, FullTrackingWithVersion> execute(LeanGridConfigSearchRequest request) throws Exception {
        return execute(resolver.query(
                request.getLimit(),
                request.getOffset(),
                request.getSearchFilter(),
                request.getSortColumns()),
                request.getSearchOutputTarget());
    }
}
