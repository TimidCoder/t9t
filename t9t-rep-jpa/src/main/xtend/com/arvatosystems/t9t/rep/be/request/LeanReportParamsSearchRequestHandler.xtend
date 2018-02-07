package com.arvatosystems.t9t.rep.be.request

import com.arvatosystems.t9t.base.jpa.impl.AbstractLeanSearchRequestHandler
import com.arvatosystems.t9t.base.search.Description
import com.arvatosystems.t9t.rep.jpa.entities.ReportParamsEntity
import com.arvatosystems.t9t.rep.jpa.persistence.IReportParamsEntityResolver
import com.arvatosystems.t9t.rep.request.LeanReportParamsSearchRequest
import de.jpaw.dp.Jdp

class LeanReportParamsSearchRequestHandler extends AbstractLeanSearchRequestHandler<LeanReportParamsSearchRequest, ReportParamsEntity> {
    public new() {
        super(Jdp.getRequired(IReportParamsEntityResolver),
            [ return new Description(null, reportParamsId, reportParamsId, false, false) ]
        )
    }
}
