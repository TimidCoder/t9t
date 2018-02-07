package com.arvatosystems.t9t.base.jpa.entityListeners;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.arvatosystems.t9t.base.entities.FullTracking;

import de.jpaw.bonaparte.jpa.BonaPersistableTracking;

public class FullTrackingEntityListener extends AbstractEntityListener<FullTracking> {

    @PreUpdate
    @Override
    public void preUpdate(BonaPersistableTracking<FullTracking> entity) {
        FullTracking tr = entity.ret$Tracking();
        updateTracking(tr, true);
        entity.put$Tracking(tr);
    }

    @PrePersist
    @Override
    public void prePersist(BonaPersistableTracking<FullTracking> entity) {
        FullTracking tr = new FullTracking();
        createTracking(tr);
        entity.put$Tracking(tr);
    }
}
