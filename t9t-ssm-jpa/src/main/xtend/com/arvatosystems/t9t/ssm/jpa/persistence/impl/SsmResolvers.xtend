package com.arvatosystems.t9t.ssm.jpa.persistence.impl

import com.arvatosystems.t9t.annotations.jpa.AutoResolver42
import com.arvatosystems.t9t.ssm.SchedulerSetupRef
import com.arvatosystems.t9t.ssm.jpa.entities.SchedulerSetupEntity

@AutoResolver42
class SsmResolvers {
    // @AllCanAccessGlobalTenant - no, why should they?
    def SchedulerSetupEntity getSchedulerSetupEntity              (SchedulerSetupRef   entityRef, boolean onlyActive) { return null; }
}
