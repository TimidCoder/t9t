package com.arvatosystems.t9t.base.jpa.jta.impl;

import java.util.IdentityHashMap;
import java.util.Map;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.jpa.BonaPersistableNoData;
import javax.persistence.EntityManager;

public class PersistenceProviderJPAImpl extends PersistenceProviderJPAJtaImpl {

    public PersistenceProviderJPAImpl(EntityManager em) {
        super(em);
    }

    public final Map<BonaPersistableNoData<?, ?>, Map<Class<? extends BonaPortable>, BonaPortable>> dtoCache
        = new IdentityHashMap<BonaPersistableNoData<?, ?>, Map<Class<? extends BonaPortable>, BonaPortable>>(61);
}
