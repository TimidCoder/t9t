package com.arvatosystems.t9t.client

import com.arvatosystems.t9t.base.api.ServiceRequest
import com.arvatosystems.t9t.server.services.IUnauthenticatedServiceRequestExecutor
import de.jpaw.dp.Fallback
import de.jpaw.dp.Singleton

@Singleton
@Fallback
class DummyUnAuth implements IUnauthenticatedServiceRequestExecutor {

    override execute(ServiceRequest arg0) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub")
    }

    override executeTrusted(ServiceRequest srq) {
        throw new UnsupportedOperationException("TODO: auto-generated method stub")
    }
}
