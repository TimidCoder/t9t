package com.arvatosystems.t9t.base.jpa.impl;

import java.io.Serializable;

import com.arvatosystems.t9t.base.jpa.IResolverCompositeKey;

import de.jpaw.bonaparte.jpa.BonaPersistableKey;
import de.jpaw.bonaparte.jpa.BonaPersistableTracking;
import de.jpaw.bonaparte.pojos.api.CompositeKeyRef;
import de.jpaw.bonaparte.pojos.api.TrackingBase;
import de.jpaw.dp.Alternative;

/** Base implementation of the IEntityResolver interface, suitable for tables with a natural key. */
@Alternative
public abstract class AbstractResolverCompositeKey<
    REF extends CompositeKeyRef,  //REQKEY
    KEY extends Serializable,   // can be removed! //REQKEY
    TRACKING extends TrackingBase,
    ENTITY extends BonaPersistableKey<KEY> & BonaPersistableTracking<TRACKING>
    > extends AbstractResolverAnyKey<KEY, TRACKING, ENTITY> implements IResolverCompositeKey<REF, KEY, TRACKING, ENTITY> {

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
        return getEntityDataByGenericKey(resolveNestedRefs(entityRef), onlyActive);
    }

}
