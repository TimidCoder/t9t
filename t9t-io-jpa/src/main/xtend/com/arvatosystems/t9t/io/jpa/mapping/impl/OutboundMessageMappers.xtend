package com.arvatosystems.t9t.io.jpa.mapping.impl


import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.io.OutboundMessageDTO
import com.arvatosystems.t9t.io.jpa.entities.OutboundMessageEntity
import com.arvatosystems.t9t.io.jpa.persistence.IOutboundMessageEntityResolver

@AutoMap42
public class OutboundMessageMappers {
    IOutboundMessageEntityResolver resolver

    def void d2eOutboundMessageDTO(OutboundMessageEntity entity, OutboundMessageDTO dto, boolean onlyActive) {}
    def void e2dOutboundMessageDTO(OutboundMessageEntity entity, OutboundMessageDTO dto) {}
}
