package com.arvatosystems.t9t.ssm.be.request

import com.arvatosystems.t9t.base.jpa.impl.AbstractLeanSearchRequestHandler
import com.arvatosystems.t9t.base.search.Description
import com.arvatosystems.t9t.ssm.jpa.entities.SchedulerSetupEntity
import com.arvatosystems.t9t.ssm.jpa.persistence.ISchedulerSetupEntityResolver
import com.arvatosystems.t9t.ssm.request.LeanSchedulerSetupSearchRequest
import de.jpaw.dp.Jdp

class LeanSchedulerSetupSearchRequestHandler extends AbstractLeanSearchRequestHandler<LeanSchedulerSetupSearchRequest, SchedulerSetupEntity> {
    public new() {
        super(Jdp.getRequired(ISchedulerSetupEntityResolver),
            [ return new Description(null, schedulerId, name, false, false) ]
        )
    }
}
