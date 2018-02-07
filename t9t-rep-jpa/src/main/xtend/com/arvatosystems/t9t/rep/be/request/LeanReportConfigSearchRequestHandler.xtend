package com.arvatosystems.t9t.rep.be.request

import com.arvatosystems.t9t.base.jpa.impl.AbstractLeanSearchRequestHandler
import com.arvatosystems.t9t.base.search.Description
import com.arvatosystems.t9t.rep.jpa.entities.ReportConfigEntity
import com.arvatosystems.t9t.rep.jpa.persistence.IReportConfigEntityResolver
import com.arvatosystems.t9t.rep.request.LeanReportConfigSearchRequest
import de.jpaw.dp.Jdp

class LeanReportConfigSearchRequestHandler extends AbstractLeanSearchRequestHandler<LeanReportConfigSearchRequest, ReportConfigEntity> {
    public new() {
        super(Jdp.getRequired(IReportConfigEntityResolver),
            [ return new Description(null, reportConfigId, name, false, false) ]
        )
    }
}
