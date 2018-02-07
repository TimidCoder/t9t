package com.arvatosystems.t9t.event.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.event.ListenerConfigDTO
import com.arvatosystems.t9t.event.jpa.entities.ListenerConfigEntity
import com.arvatosystems.t9t.event.jpa.persistence.IListenerConfigEntityResolver

@AutoMap42
public class ListenerConfigMappers {

    IListenerConfigEntityResolver entityResolver

    @AutoHandler("S42")
    def void e2dListenerConfigDTO         (ListenerConfigEntity entity, ListenerConfigDTO dto) {}
}
