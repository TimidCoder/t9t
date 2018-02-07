package com.arvatosystems.t9t.batch.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.batch.StatisticsDTO
import com.arvatosystems.t9t.batch.jpa.entities.StatisticsEntity
import com.arvatosystems.t9t.batch.jpa.persistence.IStatisticsEntityResolver

@AutoMap42
class StatisticsMappers {
    IStatisticsEntityResolver entityResolver
    @AutoHandler("S42")
    def void e2dStatisticsDTO(StatisticsEntity entity, StatisticsDTO dto) {}
}
