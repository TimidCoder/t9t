package com.arvatosystems.t9t.auth.be.request;

import com.arvatosystems.t9t.auth.UserDTO;
import com.arvatosystems.t9t.auth.request.UserSearchRequest;
import com.arvatosystems.t9t.auth.services.IUserResolver;
import com.arvatosystems.t9t.base.be.impl.AbstractSearchBERequestHandler;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.search.ReadAllResponse;

import de.jpaw.dp.Jdp;

public class UserSearchRequestHandler extends AbstractSearchBERequestHandler<UserDTO, FullTrackingWithVersion, UserSearchRequest> {

    // @Inject
    protected final IUserResolver resolver = Jdp.getRequired(IUserResolver.class);

    @Override
    public ReadAllResponse<UserDTO, FullTrackingWithVersion> execute(UserSearchRequest request) {
        return execute(resolver.query(
                request.getLimit(),
                request.getOffset(),
                request.getSearchFilter(),
                request.getSortColumns()),
                request.getSearchOutputTarget());
    }
}
