package com.arvatosystems.t9t.base.jpa.entityListeners;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.joda.time.Instant;

import com.arvatosystems.t9t.base.entities.SessionTracking;

import de.jpaw.bonaparte.jpa.BonaPersistableTracking;

public class SessionTrackingEntityListener extends AbstractEntityListener<SessionTracking>{

    @PreUpdate
    @Override
    public void preUpdate(BonaPersistableTracking<SessionTracking> entity) {
        SessionTracking tr = entity.ret$Tracking();
        tr.setMTimestamp(Instant.now());
        tr.setMTechUserId(getCutUserId());
        entity.put$Tracking(tr);
    }

    @PrePersist
    @Override
    public void prePersist(BonaPersistableTracking<SessionTracking> entity) {
        SessionTracking tr = entity.ret$Tracking();
        tr.setCTimestamp(Instant.now());
        tr.setCTechUserId(getCutUserId());
        tr.setMTimestamp(tr.getCTimestamp());
        tr.setMTechUserId(tr.getCTechUserId());
        entity.put$Tracking(tr);
    }
}
