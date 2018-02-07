package com.arvatosystems.t9t.base.jpa.impl;

import com.arvatosystems.t9t.base.jpa.IResolverSuperclassKey;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.jpa.BonaPersistableKey;
import de.jpaw.bonaparte.jpa.BonaPersistableTracking;
import de.jpaw.bonaparte.pojos.api.TrackingBase;
import de.jpaw.dp.Alternative;

/** Base implementation of the IEntityResolver interface, suitable for tables with a natural key. */
@Alternative
public abstract class AbstractResolverSuperclassKey<
    REF extends BonaPortable,
    KEY extends REF,
    TRACKING extends TrackingBase,
    ENTITY extends BonaPersistableKey<KEY> & BonaPersistableTracking<TRACKING>
    > extends AbstractResolverAnyKey<KEY, TRACKING, ENTITY> implements IResolverSuperclassKey<REF, KEY, TRACKING, ENTITY> {

    @Override
    public final boolean hasArtificialPrimaryKey() {
        return false;
    }

    /**
     * Subroutine used to resolve nested references (i.e. objects containing Object references itself). This code has to be provided by hand, i.e. standard
     * implementation is just a hook and performs no activity.
     *
     * A typical implementation would work as follows: if (arg instanceof MySpecialArg) { // manual code to convert to MyStandardArg return convertedArg; }
     *
     * If no matching type is found, the method should always fall back and return super.resolverNestedRefs() to allow nested implementations.
     * */
    protected REF resolveNestedRefs(REF arg) {
        return arg;
    }

    @Override
    public ENTITY getEntityData(REF entityRef, boolean onlyActive) {
        if (entityRef == null) {
            return null;        // play null-safe
        }

        Class<KEY> keyClass = getKeyClass();
        entityRef = resolveNestedRefs(entityRef);
        if (keyClass.isAssignableFrom(entityRef.getClass())) {
            // access via primary key or supertype of it
            KEY key = (keyClass != entityRef.getClass()) ? entityRef.copyAs(keyClass) : (KEY) entityRef;  // if it's some supertype: copy it down!
            return getEntityDataForKey(key, onlyActive);
        }
        return getEntityDataByGenericKey(entityRef, onlyActive);
    }

}
