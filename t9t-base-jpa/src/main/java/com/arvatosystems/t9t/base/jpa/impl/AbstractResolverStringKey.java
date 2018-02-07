package com.arvatosystems.t9t.base.jpa.impl;

import com.arvatosystems.t9t.base.jpa.IResolverStringKey;

import de.jpaw.bonaparte.jpa.BonaPersistableKey;
import de.jpaw.bonaparte.jpa.BonaPersistableTracking;
import de.jpaw.bonaparte.pojos.api.TrackingBase;
import de.jpaw.dp.Alternative;

/** Base implementation of the IEntityResolver interface, suitable for tables with a natural key. */
@Alternative
public abstract class AbstractResolverStringKey<
    TRACKING extends TrackingBase,
    ENTITY extends BonaPersistableKey<String> & BonaPersistableTracking<TRACKING>
    > extends AbstractResolverAnyKey<String, TRACKING, ENTITY> implements IResolverStringKey<TRACKING, ENTITY> {

    @Override
    public final boolean hasArtificialPrimaryKey() {
        return false;
    }
}
