package com.arvatosystems.t9t.core.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.core.CannedRequestDTO
import com.arvatosystems.t9t.core.jpa.entities.CannedRequestEntity
import com.arvatosystems.t9t.core.jpa.persistence.ICannedRequestEntityResolver

@AutoMap42
public class CannedRequestMappers {
    ICannedRequestEntityResolver entityResolver
    @AutoHandler("R42")
    def void e2dCannedRequestDTO(CannedRequestEntity entity, CannedRequestDTO dto) {}
}
