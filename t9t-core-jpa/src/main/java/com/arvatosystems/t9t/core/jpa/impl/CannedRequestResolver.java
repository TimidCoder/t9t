package com.arvatosystems.t9t.core.jpa.impl;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.jpa.impl.AbstractJpaResolver;
import com.arvatosystems.t9t.core.CannedRequestDTO;
import com.arvatosystems.t9t.core.CannedRequestRef;
import com.arvatosystems.t9t.core.jpa.entities.CannedRequestEntity;
import com.arvatosystems.t9t.core.jpa.mapping.ICannedRequestDTOMapper;
import com.arvatosystems.t9t.core.jpa.persistence.ICannedRequestEntityResolver;
import com.arvatosystems.t9t.core.services.ICannedRequestResolver;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Singleton;

@Singleton
public class CannedRequestResolver extends AbstractJpaResolver<CannedRequestRef, CannedRequestDTO, FullTrackingWithVersion, CannedRequestEntity> implements ICannedRequestResolver {

    public CannedRequestResolver() {
        super("CannedRequest", Jdp.getRequired(ICannedRequestEntityResolver.class), Jdp.getRequired(ICannedRequestDTOMapper.class));
    }

    @Override
    public CannedRequestRef createKey(Long ref) {
        return ref == null ? null : new CannedRequestRef(ref);
    }
}
