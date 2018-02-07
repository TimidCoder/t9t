package com.arvatosystems.t9t.base.jpa.entityListeners;

import javax.persistence.PrePersist;

import org.joda.time.Instant;

import com.arvatosystems.t9t.base.entities.MessageTracking;

import de.jpaw.bonaparte.jpa.BonaPersistableTracking;

public class MessageTrackingEntityListener extends AbstractEntityListener<MessageTracking> {

    @PrePersist
    @Override
    public void prePersist(BonaPersistableTracking<MessageTracking> entity) {
        MessageTracking tr = new MessageTracking();
        tr.setCTimestamp(Instant.now());
        tr.setCTechUserId(getCutUserId());
        entity.put$Tracking(tr);
    }

    @Override
    public void preUpdate(BonaPersistableTracking<MessageTracking> entity) {
        //  no updates ?
    }
}
