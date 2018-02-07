package com.arvatosystems.t9t.auth.jpa.impl;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.arvatosystems.t9t.auth.ApiKeyDTO;
import com.arvatosystems.t9t.auth.ApiKeyRef;
import com.arvatosystems.t9t.auth.jpa.entities.ApiKeyEntity;
import com.arvatosystems.t9t.auth.jpa.mapping.IApiKeyDTOMapper;
import com.arvatosystems.t9t.auth.jpa.persistence.IApiKeyEntityResolver;
import com.arvatosystems.t9t.auth.services.IApiKeyResolver;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.jpa.impl.AbstractJpaResolver;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Singleton;

@Singleton
public class ApiKeyResolver extends AbstractJpaResolver<ApiKeyRef, ApiKeyDTO, FullTrackingWithVersion, ApiKeyEntity> implements IApiKeyResolver {

    public ApiKeyResolver() {
        super("ApiKey", Jdp.getRequired(IApiKeyEntityResolver.class), Jdp.getRequired(IApiKeyDTOMapper.class));
    }

    @Override
    protected TypedQuery<ApiKeyEntity> createQuery(EntityManager em) {
        return em.createQuery("SELECT e FROM ApiKeyEntity e", ApiKeyEntity.class);
    }

    @Override
    public ApiKeyRef createKey(Long ref) {
        return ref == null ? null : new ApiKeyRef(ref);
    }
}
