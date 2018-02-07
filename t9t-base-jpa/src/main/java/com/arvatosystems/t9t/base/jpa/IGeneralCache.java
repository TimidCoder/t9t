package com.arvatosystems.t9t.base.jpa;

import java.util.Collection;

import de.jpaw.bonaparte.jpa.BonaPersistableKey;
import de.jpaw.bonaparte.pojos.apiw.Ref;

// TODO: move to t9t
public interface IGeneralCache<R extends Ref, E extends BonaPersistableKey<Long>> {
    /** Return an entity from an existing known pool, or query from database. */
    E getEntityData(R ref, Collection<E> knownPool);

    /** Return an entity ref from an existing known pool, or query from database. */
    Long getRef(R ref, Collection<E> knownPool);
}
