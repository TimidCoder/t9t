package com.arvatosystems.t9t.io.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.io.DataSinkDTO
import com.arvatosystems.t9t.io.DataSinkKey
import com.arvatosystems.t9t.io.jpa.entities.DataSinkEntity
import com.arvatosystems.t9t.io.jpa.mapping.ICsvConfigurationDTOMapper
import com.arvatosystems.t9t.io.jpa.persistence.ICsvConfigurationEntityResolver
import com.arvatosystems.t9t.io.jpa.persistence.IDataSinkEntityResolver
import com.arvatosystems.t9t.io.DataSinkFilterProps

@AutoMap42
public class DataSinkMappers {
    IDataSinkEntityResolver         entityResolver
    ICsvConfigurationEntityResolver csvResolver
    ICsvConfigurationDTOMapper      csvMapper

    def void e2dDataSinkDTO         (DataSinkEntity entity, DataSinkDTO dto) {
        dto.csvConfigurationRef     = csvMapper.mapToDto(entity.csvConfigurationRef)
    }

    def void d2eDataSinkDTO         (DataSinkEntity entity, DataSinkDTO dto, boolean onlyActive) {
        entity.csvConfigurationRef = csvResolver.getRef(dto.csvConfigurationRef, onlyActive)
    }

    def void e2dDataSinkKey         (DataSinkEntity entity, DataSinkKey dto) {}
    def void e2dDataSinkFilterProps (DataSinkEntity entity, DataSinkFilterProps dto) {}
}
