package com.arvatosystems.t9t.core.be.request

import com.arvatosystems.t9t.base.jpa.impl.AbstractLeanSearchRequestHandler
import com.arvatosystems.t9t.base.search.Description
import com.arvatosystems.t9t.core.jpa.entities.CannedRequestEntity
import com.arvatosystems.t9t.core.jpa.persistence.ICannedRequestEntityResolver
import com.arvatosystems.t9t.core.request.LeanCannedRequestSearchRequest
import de.jpaw.dp.Jdp

class LeanCannedRequestSearchRequestHandler extends AbstractLeanSearchRequestHandler<LeanCannedRequestSearchRequest, CannedRequestEntity> {
    public new() {
        super(Jdp.getRequired(ICannedRequestEntityResolver),
            [ return new Description(null, requestId, name, false, false) ]
        )
    }
}
