package com.arvatosystems.t9t.base.jpa.rl.impl;

import java.util.IdentityHashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.jpa.BonaPersistableNoData;
import de.jpaw.bonaparte.jpa.refs.PersistenceProviderJPARLImpl;

public class PersistenceProviderJPAImpl extends PersistenceProviderJPARLImpl {

    public PersistenceProviderJPAImpl(EntityManagerFactory emf) {
        super(emf);
    }

    public final Map<BonaPersistableNoData<?, ?>, Map<Class<? extends BonaPortable>, BonaPortable>> dtoCache
        = new IdentityHashMap<BonaPersistableNoData<?, ?>, Map<Class<? extends BonaPortable>, BonaPortable>>(61);
}
