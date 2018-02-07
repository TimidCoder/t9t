package com.arvatosystems.t9t.ssm.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.core.jpa.mapping.ICannedRequestDTOMapper
import com.arvatosystems.t9t.core.jpa.persistence.ICannedRequestEntityResolver
import com.arvatosystems.t9t.ssm.SchedulerSetupDTO
import com.arvatosystems.t9t.ssm.SchedulerSetupKey
import com.arvatosystems.t9t.ssm.jpa.entities.SchedulerSetupEntity
import com.arvatosystems.t9t.ssm.jpa.persistence.ISchedulerSetupEntityResolver

@AutoMap42
public class SchedulerSetupEntityMappers {
    ISchedulerSetupEntityResolver   schedulerResolver
    ICannedRequestEntityResolver    requestResolver
    ICannedRequestDTOMapper         requestMapper

//    @AutoHandler("S42")
    def void e2dSchedulerSetupDTO(SchedulerSetupEntity entity, SchedulerSetupDTO dto) {
        dto.request = requestMapper.mapToDto(entity.cannedRequest)
    }
    def void d2eSchedulerSetupDTO(SchedulerSetupEntity entity, SchedulerSetupDTO dto, boolean onlyActive) {
        entity.request = requestResolver.getRef(dto.request, onlyActive)
    }
    def void e2dSchedulerSetupKey(SchedulerSetupEntity entity, SchedulerSetupKey dto) {}
}
