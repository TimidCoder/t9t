package com.arvatosystems.t9t.base.jpa;

import com.arvatosystems.t9t.base.T9tException;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.jpa.BonaPersistableKey;
import de.jpaw.bonaparte.jpa.BonaPersistableTracking;
import de.jpaw.bonaparte.pojos.api.TrackingBase;

/** Defines methods to return either the artificial key (via any key) or the full JPA entity (via some key).
 *
 * For every relevant JPA entity, one separate interface is extended from this one, which works as a customization target for CDI.
 * If the JPA entity is extended as part of customization, the base interface will stay untouched, but its implementation must point
 * to a customized resolver, inheriting the base resolver.
 */
public interface IResolverNewCompositeKey<
    REF extends BonaPortable,
    KEY extends REF,
    TRACKING extends TrackingBase,
    ENTITY extends BonaPersistableKey<KEY> & BonaPersistableTracking<TRACKING>
    > extends IResolverAnyKey<KEY, TRACKING, ENTITY> {

    /** Return the full JPA entity for any given relevant key.
     * Returns null if the parameter entityRef is null.
     * Throws an exception (T9tException.RECORD_DOES_NOT_EXIST) if there is no data record for the specified entityRef.
     * Throws an exception (T9tException.RECORD_INACTIVE) if the record exists, but has been marked inactive and parameter onlyActive = true.
     *
     * @param entityRef The input DTO, which inherits a suitable reference to the object.
     * @param onlyActive True if inactive records should be treated as nonexisting.
     * @return ENTITY
     * @throws T9tException
     */
    ENTITY getEntityData(KEY entityRef, boolean onlyActive);
}
