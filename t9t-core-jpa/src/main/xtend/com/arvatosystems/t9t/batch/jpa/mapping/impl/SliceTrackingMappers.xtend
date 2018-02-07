package com.arvatosystems.t9t.batch.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.batch.SliceTrackingDTO
import com.arvatosystems.t9t.batch.jpa.entities.SliceTrackingEntity
import com.arvatosystems.t9t.batch.jpa.persistence.ISliceTrackingEntityResolver

@AutoMap42
class SliceTrackingMappers {
    ISliceTrackingEntityResolver entityResolver
    @AutoHandler("S42")
    def void e2dSliceTrackingDTO(SliceTrackingEntity entity, SliceTrackingDTO dto) {}
}
