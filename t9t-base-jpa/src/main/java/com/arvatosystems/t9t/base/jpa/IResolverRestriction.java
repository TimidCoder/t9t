package com.arvatosystems.t9t.base.jpa;

import com.arvatosystems.t9t.base.T9tException;

import de.jpaw.bonaparte.jpa.BonaPersistableKey;
import de.jpaw.bonaparte.jpa.BonaPersistableTracking;
import de.jpaw.bonaparte.pojos.api.TrackingBase;
import de.jpaw.bonaparte.pojos.apiw.Ref;

/**
 * Define restriction that can be applied before/after entity is resolved.
 * Implementation not necessarily implements all {@linkplain ResolverRestriction#apply()} methods.
 * If it's not implemented, a {@linkplain UnsupportedOperationException} should be throw instead.
 * As to which {@linkplain ResolverRestriction#apply()} method to be implemented, this really depends
 * on the structure of the entity as well as the data required for the restriction to be applied.
 * <p>
 * Most of the time, restriction should be called "wrapping" the actual resolve method itself:
 * <pre>
 *    locationsResolver.findActive(restriction.apply(someParams.getLocationRef().getObjectRef()), true);
 * </pre>
 * @author LIEE001
 * @param <T> reference type
 * @param <TRACKING> tracking type
 * @param <ENTITY> entity type
 */
public interface IResolverRestriction <T extends Ref, TRACKING extends TrackingBase, ENTITY extends BonaPersistableKey<Long> & BonaPersistableTracking<TRACKING>> {

    /**
     * Apply restriction. This should be mainly used before resolving the entity itself.
     * @param ref entity object reference
     * @return the passed entity object reference
     * @throws T9tException if the passed object reference is restricted!
     */
    Long apply(Long ref);

    /**
     * Apply restriction. This should be mainly used before resolving the entity itself.
     * @param ref entity reference
     * @return the passed entity reference
     * @throws T9tException if the passed reference is restricted!
     */
    T apply(T ref);

    /**
     * Apply restriction. This should be mainly used after resolving the entity itself.
     * Depending on the type of restriction, sometimes its NOT possible to apply the restriction
     * without getting the actual entity data first i.e. the restriction depends on the data which
     * is not available on the entity object reference or entity key.
     * @param entity entity
     * @return the passed entity
     * @throws T9tException if the passed entity is restricted!
     */
    ENTITY apply(ENTITY entity);
}
