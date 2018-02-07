package com.arvatosystems.t9t.auth.be.request;

import com.arvatosystems.t9t.auth.UserTenantRoleDTO;
import com.arvatosystems.t9t.auth.UserTenantRoleInternalKey;
import com.arvatosystems.t9t.auth.UserTenantRoleRef;
import com.arvatosystems.t9t.auth.jpa.entities.UserTenantRoleEntity;
import com.arvatosystems.t9t.auth.jpa.mapping.IUserTenantRoleDTOMapper;
import com.arvatosystems.t9t.auth.jpa.persistence.IUserTenantRoleEntityResolver;
import com.arvatosystems.t9t.auth.request.UserTenantRoleCrudRequest;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.jpa.impl.AbstractCrudCompositeRefKey42RequestHandler;
import com.arvatosystems.t9t.base.services.RequestContext;

import de.jpaw.dp.Jdp;

public class UserTenantRoleCrudRequestHandler extends AbstractCrudCompositeRefKey42RequestHandler<
    UserTenantRoleRef, UserTenantRoleInternalKey, UserTenantRoleDTO, FullTrackingWithVersion,
    UserTenantRoleCrudRequest, UserTenantRoleEntity> {

    private final IUserTenantRoleDTOMapper mapper = Jdp.getRequired(IUserTenantRoleDTOMapper.class);

    private final IUserTenantRoleEntityResolver resolver = Jdp.getRequired(IUserTenantRoleEntityResolver.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, UserTenantRoleCrudRequest params) {
        return execute(mapper, resolver, params);
    }
}
