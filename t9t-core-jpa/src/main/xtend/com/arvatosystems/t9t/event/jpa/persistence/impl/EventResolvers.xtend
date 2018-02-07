package com.arvatosystems.t9t.event.jpa.persistence.impl

import com.arvatosystems.t9t.annotations.jpa.AutoResolver42
import com.arvatosystems.t9t.event.ListenerConfigRef
import com.arvatosystems.t9t.event.SubscriberConfigRef
import com.arvatosystems.t9t.event.jpa.entities.SubscriberConfigEntity
import com.arvatosystems.t9t.event.jpa.entities.ListenerConfigEntity

@AutoResolver42
class EventResolvers {
    def SubscriberConfigEntity  getSubscriberConfigEntity(SubscriberConfigRef entityRef, boolean onlyActive) { return null; }
    def ListenerConfigEntity    getListenerConfigEntity  (ListenerConfigRef   entityRef, boolean onlyActive) { return null; }
}
