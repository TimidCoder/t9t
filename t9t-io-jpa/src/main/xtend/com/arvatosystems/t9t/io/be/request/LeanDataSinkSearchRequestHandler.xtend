package com.arvatosystems.t9t.io.be.request

import com.arvatosystems.t9t.base.jpa.impl.AbstractLeanSearchRequestHandler
import com.arvatosystems.t9t.base.search.Description
import com.arvatosystems.t9t.io.jpa.entities.DataSinkEntity
import com.arvatosystems.t9t.io.jpa.persistence.IDataSinkEntityResolver
import com.arvatosystems.t9t.io.request.LeanDataSinkSearchRequest
import de.jpaw.dp.Jdp

class LeanDataSinkSearchRequestHandler extends AbstractLeanSearchRequestHandler<LeanDataSinkSearchRequest, DataSinkEntity> {
    public new() {
        super(Jdp.getRequired(IDataSinkEntityResolver),
            [ return new Description(null, dataSinkId, description ?: '?', false, false) ]
        )
    }
}
