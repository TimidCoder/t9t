package com.arvatosystems.t9t.base.jpa;

import de.jpaw.bonaparte.jpa.BonaPersistableKey;
import de.jpaw.bonaparte.jpa.BonaPersistableTracking;
import de.jpaw.bonaparte.pojos.api.TrackingBase;

/** Defines methods to return either the artificial key (via any key) or the full JPA entity (via some key).
 *
 * For every relevant JPA entity, one separate interface is extended from this one, which works as a customization target for CDI.
 * If the JPA entity is extended as part of customization, the base interface will stay untouched, but its implementation must point
 * to a customized resolver, inheriting the base resolver.
 */
public interface IResolverStringKey42<
    TRACKING extends TrackingBase,
    ENTITY extends BonaPersistableKey<String> & BonaPersistableTracking<TRACKING>
    > extends IResolverAnyKey42<String, TRACKING, ENTITY> {

    default ENTITY getEntityData(String entityRef, boolean onlyActive) {
        return getEntityDataForKey(entityRef, onlyActive);
    }
}
