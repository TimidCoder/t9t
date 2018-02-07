package com.arvatosystems.t9t.ssm.services;

import com.arvatosystems.t9t.ssm.SchedulerSetupDTO;

public interface ISsmPersistenceAccess {
    /** Read a specific scheduler setup */
    SchedulerSetupDTO getPricingRuleByRef(Long ref);
}
