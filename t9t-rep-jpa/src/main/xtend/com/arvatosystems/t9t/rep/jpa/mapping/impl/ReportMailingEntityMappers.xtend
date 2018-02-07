package com.arvatosystems.t9t.rep.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.rep.ReportMailingDTO
import com.arvatosystems.t9t.rep.ReportMailingKey
import com.arvatosystems.t9t.rep.jpa.entities.ReportMailingEntity
import com.arvatosystems.t9t.rep.jpa.persistence.IReportMailingEntityResolver

@AutoMap42
public class ReportMailingEntityMappers {
    IReportMailingEntityResolver reportMailingResolver

    @AutoHandler("CASP42")
    def void e2dReportMailingDTO(ReportMailingEntity entity, ReportMailingDTO dto) {}

    def void d2eReportMailingDTO(ReportMailingEntity entity, ReportMailingDTO dto, boolean onlyActive) {}

    def void e2dReportMailingKey(ReportMailingEntity entity, ReportMailingKey dto) {}
}
