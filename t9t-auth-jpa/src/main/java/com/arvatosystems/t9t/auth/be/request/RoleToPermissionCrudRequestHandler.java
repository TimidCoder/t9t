package com.arvatosystems.t9t.auth.be.request;

import com.arvatosystems.t9t.auth.RoleToPermissionDTO;
import com.arvatosystems.t9t.auth.RoleToPermissionInternalKey;
import com.arvatosystems.t9t.auth.RoleToPermissionRef;
import com.arvatosystems.t9t.auth.jpa.entities.RoleToPermissionEntity;
import com.arvatosystems.t9t.auth.jpa.mapping.IRoleToPermissionDTOMapper;
import com.arvatosystems.t9t.auth.jpa.persistence.IRoleToPermissionEntityResolver;
import com.arvatosystems.t9t.auth.request.RoleToPermissionCrudRequest;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.jpa.impl.AbstractCrudCompositeRefKey42RequestHandler;
import com.arvatosystems.t9t.base.services.RequestContext;

import de.jpaw.dp.Jdp;

public class RoleToPermissionCrudRequestHandler extends AbstractCrudCompositeRefKey42RequestHandler<
    RoleToPermissionRef, RoleToPermissionInternalKey, RoleToPermissionDTO, FullTrackingWithVersion,
    RoleToPermissionCrudRequest, RoleToPermissionEntity> {

    private final IRoleToPermissionDTOMapper mapper = Jdp.getRequired(IRoleToPermissionDTOMapper.class);

    private final IRoleToPermissionEntityResolver resolver = Jdp.getRequired(IRoleToPermissionEntityResolver.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, RoleToPermissionCrudRequest params) {
        return execute(mapper, resolver, params);
    }
}
