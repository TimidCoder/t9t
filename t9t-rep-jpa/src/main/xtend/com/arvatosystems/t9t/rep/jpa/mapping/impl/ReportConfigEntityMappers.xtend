package com.arvatosystems.t9t.rep.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.rep.ReportConfigDTO
import com.arvatosystems.t9t.rep.ReportConfigKey
import com.arvatosystems.t9t.rep.jpa.entities.ReportConfigEntity
import com.arvatosystems.t9t.rep.jpa.persistence.IReportConfigEntityResolver

@AutoMap42
public class ReportConfigEntityMappers {
    IReportConfigEntityResolver reportConfigResolver
    @AutoHandler("CAP42")
    def void e2dReportConfigDTO(ReportConfigEntity entity, ReportConfigDTO dto) {}
    def void e2dReportConfigKey(ReportConfigEntity entity, ReportConfigKey dto) {}
}
