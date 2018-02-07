package com.arvatosystems.t9t.base.jpa.entityListeners;

import javax.persistence.PrePersist;

import com.arvatosystems.t9t.base.entities.FullTracking;
import com.arvatosystems.t9t.base.entities.WriteTracking;

import de.jpaw.bonaparte.jpa.BonaPersistableTracking;

public class WriteTrackingEntityListener extends AbstractEntityListener<WriteTracking> {

    @PrePersist
    @Override
    public void prePersist(BonaPersistableTracking<WriteTracking> entity) {
        FullTracking tr = new FullTracking();
        createTracking(tr);
        entity.put$Tracking(tr);
    }

    @Override
    public void preUpdate(BonaPersistableTracking<WriteTracking> entity) {
    }

}
