package com.arvatosystems.t9t.event.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.event.SubscriberConfigDTO
import com.arvatosystems.t9t.event.jpa.entities.SubscriberConfigEntity
import com.arvatosystems.t9t.event.jpa.persistence.ISubscriberConfigEntityResolver

@AutoMap42
public class SubscriberConfigMappers {

    ISubscriberConfigEntityResolver entityResolver

    @AutoHandler("S42")
    def void e2dSubscriberConfigDTO         (SubscriberConfigEntity entity, SubscriberConfigDTO dto) {}
}
