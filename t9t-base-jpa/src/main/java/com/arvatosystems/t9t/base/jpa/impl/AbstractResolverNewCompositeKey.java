package com.arvatosystems.t9t.base.jpa.impl;

import com.arvatosystems.t9t.base.jpa.IResolverNewCompositeKey;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.jpa.BonaPersistableKey;
import de.jpaw.bonaparte.jpa.BonaPersistableTracking;
import de.jpaw.bonaparte.pojos.api.TrackingBase;
import de.jpaw.dp.Alternative;

/** Base implementation of the IEntityResolver interface, suitable for tables with a natural key. */
@Alternative
public abstract class AbstractResolverNewCompositeKey<
    REF extends BonaPortable,  //REQKEY
    KEY extends REF,   // can be removed! //REQKEY
    TRACKING extends TrackingBase,
    ENTITY extends BonaPersistableKey<KEY> & BonaPersistableTracking<TRACKING>
    > extends AbstractResolverAnyKey<KEY, TRACKING, ENTITY> implements IResolverNewCompositeKey<REF, KEY, TRACKING, ENTITY> {

    @Override
    public final boolean hasArtificialPrimaryKey() {
        return false;
    }

    @Override
    public ENTITY getEntityData(KEY entityRef, boolean onlyActive) {
        return getEntityDataByGenericKey(entityRef, onlyActive);
    }
}
