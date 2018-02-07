package com.arvatosystems.t9t.base.jpa;

import de.jpaw.bonaparte.jpa.BonaPersistableTracking;
import de.jpaw.bonaparte.pojos.api.TrackingBase;

public interface IEntityListener<T extends TrackingBase> {
    public void preUpdate(BonaPersistableTracking<T> entity);
    public void prePersist(BonaPersistableTracking<T> entity);
}
