package com.arvatosystems.t9t.base.jpa.impl;

import com.arvatosystems.t9t.base.jpa.IResolverLongKey42;

import de.jpaw.bonaparte.jpa.BonaPersistableKey;
import de.jpaw.bonaparte.jpa.BonaPersistableTracking;
import de.jpaw.bonaparte.pojos.api.TrackingBase;
import de.jpaw.dp.Alternative;

/** Base implementation of the IEntityResolver interface, suitable for tables with a natural key. */
@Alternative
public abstract class AbstractResolverLongKey42<
    TRACKING extends TrackingBase,
    ENTITY extends BonaPersistableKey<Long> & BonaPersistableTracking<TRACKING>
    > extends AbstractResolverAnyKey42<Long, TRACKING, ENTITY> implements IResolverLongKey42<TRACKING, ENTITY> {

    @Override
    public final boolean hasArtificialPrimaryKey() {
        return false;
    }
}
