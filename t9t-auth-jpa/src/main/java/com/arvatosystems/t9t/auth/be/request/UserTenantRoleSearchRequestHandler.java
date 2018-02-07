package com.arvatosystems.t9t.auth.be.request;

import com.arvatosystems.t9t.auth.UserTenantRoleDTO;
import com.arvatosystems.t9t.auth.jpa.mapping.IUserTenantRoleDTOMapper;
import com.arvatosystems.t9t.auth.jpa.persistence.IUserTenantRoleEntityResolver;
import com.arvatosystems.t9t.auth.request.UserTenantRoleSearchRequest;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.search.ReadAllResponse;
import com.arvatosystems.t9t.base.services.AbstractSearchRequestHandler;
import com.arvatosystems.t9t.base.services.RequestContext;

import de.jpaw.bonaparte.api.SearchFilters;
import de.jpaw.bonaparte.pojos.api.LongFilter;
import de.jpaw.dp.Jdp;

/** This search request has to add conditions on the tenant of the child entities of user and role: none of the returned objects may refer to an entity of a different non-@ tenant. */
public class UserTenantRoleSearchRequestHandler extends AbstractSearchRequestHandler<UserTenantRoleSearchRequest> {
    protected final IUserTenantRoleEntityResolver resolver = Jdp.getRequired(IUserTenantRoleEntityResolver.class);
    protected final IUserTenantRoleDTOMapper mapper = Jdp.getRequired(IUserTenantRoleDTOMapper.class);

    @Override
    public ReadAllResponse<UserTenantRoleDTO, FullTrackingWithVersion> execute(final RequestContext ctx, final UserTenantRoleSearchRequest request) throws Exception {
        mapper.processSearchPrefixForDB(request); // convert the field with searchPrefix

        final LongFilter userFilter = ctx.tenantFilter("user.tenantRef");
        final LongFilter roleFilter = ctx.tenantFilter("role.tenantRef");
        request.setSearchFilter(SearchFilters.and(request.getSearchFilter(), SearchFilters.and(userFilter, roleFilter)));
        return mapper.createReadAllResponse(resolver.search(request, null), request.getSearchOutputTarget());
    }
}
