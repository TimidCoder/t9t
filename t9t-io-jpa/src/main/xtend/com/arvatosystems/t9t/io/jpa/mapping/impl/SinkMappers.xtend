package com.arvatosystems.t9t.io.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.io.SinkDTO
import com.arvatosystems.t9t.io.jpa.entities.SinkEntity
import com.arvatosystems.t9t.io.jpa.mapping.IDataSinkFilterPropsMapper
import com.arvatosystems.t9t.io.jpa.persistence.IDataSinkEntityResolver
import com.arvatosystems.t9t.io.jpa.persistence.ISinkEntityResolver

@AutoMap42
public class SinkMappers {
    ISinkEntityResolver entityResolver
    IDataSinkEntityResolver sinkResolver
    IDataSinkFilterPropsMapper sinkMapper

    @AutoHandler("R42")
    def void d2eSinkDTO(SinkEntity entity, SinkDTO dto, boolean onlyActive) {
        entity.dataSinkRef = sinkResolver.getRef(dto.dataSinkRef, onlyActive)
    }
    def void e2dSinkDTO(SinkEntity entity, SinkDTO dto) {
        dto.dataSinkRef = sinkMapper.mapToDto(entity.dataSinkRef)
    }
}
