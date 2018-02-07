package com.arvatosystems.t9t.rep.be.request

import com.arvatosystems.t9t.base.jpa.impl.AbstractLeanSearchRequestHandler
import com.arvatosystems.t9t.base.search.Description
import com.arvatosystems.t9t.rep.jpa.entities.ReportMailingEntity
import com.arvatosystems.t9t.rep.jpa.persistence.IReportMailingEntityResolver
import com.arvatosystems.t9t.rep.request.LeanReportMailingSearchRequest
import de.jpaw.dp.Jdp

class LeanReportMailingSearchRequestHandler extends AbstractLeanSearchRequestHandler<LeanReportMailingSearchRequest, ReportMailingEntity> {
    public new() {
        super(Jdp.getRequired(IReportMailingEntityResolver),
            [ return new Description(objectRef, mailingGroupId, mailingGroupId, false, false) ]
        )
    }
}
