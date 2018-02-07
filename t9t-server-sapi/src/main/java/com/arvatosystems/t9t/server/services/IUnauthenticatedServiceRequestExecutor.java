package com.arvatosystems.t9t.server.services;

import com.arvatosystems.t9t.base.api.ServiceRequest;
import com.arvatosystems.t9t.base.api.ServiceResponse;

// implementations process unauthenticated ServiceRequests,
// first checking the authenticaton part of the request
public interface IUnauthenticatedServiceRequestExecutor {
    ServiceResponse execute(ServiceRequest srq);         // checks authorization as well
    ServiceResponse executeTrusted(ServiceRequest srq);  // checks authentication only - used for internal async messages
}
