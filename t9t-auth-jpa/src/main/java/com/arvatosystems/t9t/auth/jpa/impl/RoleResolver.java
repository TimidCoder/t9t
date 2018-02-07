package com.arvatosystems.t9t.auth.jpa.impl;

import com.arvatosystems.t9t.auth.RoleDTO;
import com.arvatosystems.t9t.auth.RoleRef;
import com.arvatosystems.t9t.auth.jpa.entities.RoleEntity;
import com.arvatosystems.t9t.auth.jpa.mapping.IRoleDTOMapper;
import com.arvatosystems.t9t.auth.jpa.persistence.IRoleEntityResolver;
import com.arvatosystems.t9t.auth.services.IRoleResolver;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.jpa.impl.AbstractJpaResolver;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Singleton;

@Singleton
public class RoleResolver extends AbstractJpaResolver<RoleRef, RoleDTO, FullTrackingWithVersion, RoleEntity> implements IRoleResolver {

    public RoleResolver() {
        super("Role", Jdp.getRequired(IRoleEntityResolver.class), Jdp.getRequired(IRoleDTOMapper.class));
    }

    @Override
    public RoleRef createKey(Long ref) {
        return ref == null ? null : new RoleRef(ref);
    }
}
