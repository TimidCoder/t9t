package com.arvatosystems.t9t.base.services;

import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.api.ServiceResponse;

/** Implementations are used to launch requests as autonomous transactions. */
public interface IAutonomousExecutor {
    ServiceResponse execute(RequestContext ctx, RequestParameters rp);
}
