package com.arvatosystems.t9t.auth.be.request;

import com.arvatosystems.t9t.auth.RoleDTO;
import com.arvatosystems.t9t.auth.request.RoleSearchRequest;
import com.arvatosystems.t9t.auth.services.IRoleResolver;
import com.arvatosystems.t9t.base.be.impl.AbstractSearchBERequestHandler;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.search.ReadAllResponse;

import de.jpaw.dp.Jdp;

public class RoleSearchRequestHandler extends AbstractSearchBERequestHandler<RoleDTO, FullTrackingWithVersion, RoleSearchRequest> {

    // @Inject
    protected final IRoleResolver resolver = Jdp.getRequired(IRoleResolver.class);

    @Override
    public ReadAllResponse<RoleDTO, FullTrackingWithVersion> execute(RoleSearchRequest request) {
        return execute(resolver.query(
                request.getLimit(),
                request.getOffset(),
                request.getSearchFilter(),
                request.getSortColumns()),
                request.getSearchOutputTarget());
    }
}
