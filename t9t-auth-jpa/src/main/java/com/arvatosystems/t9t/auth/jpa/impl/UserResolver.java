package com.arvatosystems.t9t.auth.jpa.impl;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.arvatosystems.t9t.auth.UserDTO;
import com.arvatosystems.t9t.auth.UserRef;
import com.arvatosystems.t9t.auth.jpa.entities.UserEntity;
import com.arvatosystems.t9t.auth.jpa.mapping.IUserDTOMapper;
import com.arvatosystems.t9t.auth.jpa.persistence.IUserEntityResolver;
import com.arvatosystems.t9t.auth.services.IUserResolver;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.jpa.impl.AbstractJpaResolver;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Singleton;

@Singleton
public class UserResolver extends AbstractJpaResolver<UserRef, UserDTO, FullTrackingWithVersion, UserEntity> implements IUserResolver {

    public UserResolver() {
        super("User", Jdp.getRequired(IUserEntityResolver.class), Jdp.getRequired(IUserDTOMapper.class));
    }

    @Override
    protected TypedQuery<UserEntity> createQuery(EntityManager em) {
        return em.createQuery("SELECT e FROM UserEntity e", UserEntity.class);
    }

    @Override
    public UserRef createKey(Long ref) {
        return ref == null ? null : new UserRef(ref);
    }
}
