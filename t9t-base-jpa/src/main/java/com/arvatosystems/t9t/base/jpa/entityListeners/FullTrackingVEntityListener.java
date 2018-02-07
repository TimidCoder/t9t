package com.arvatosystems.t9t.base.jpa.entityListeners;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;

import de.jpaw.bonaparte.jpa.BonaPersistableTracking;

public class FullTrackingVEntityListener extends AbstractEntityListener<FullTrackingWithVersion> {

    @PreUpdate
    @Override
    public void preUpdate(BonaPersistableTracking<FullTrackingWithVersion> entity) {
        FullTrackingWithVersion tr = entity.ret$Tracking();
        updateTracking(tr, true);
        entity.put$Tracking(tr);
    }

    @PrePersist
    @Override
    public void prePersist(BonaPersistableTracking<FullTrackingWithVersion> entity) {
        FullTrackingWithVersion tr = new FullTrackingWithVersion();
        createTracking(tr);
        tr.setVersion(0);
        entity.put$Tracking(tr);
    }
}
