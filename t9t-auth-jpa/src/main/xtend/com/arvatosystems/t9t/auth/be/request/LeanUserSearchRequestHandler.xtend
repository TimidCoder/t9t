package com.arvatosystems.t9t.auth.be.request

import com.arvatosystems.t9t.auth.jpa.entities.UserEntity
import com.arvatosystems.t9t.auth.jpa.persistence.IUserEntityResolver
import com.arvatosystems.t9t.auth.request.LeanUserSearchRequest
import com.arvatosystems.t9t.base.jpa.impl.AbstractLeanSearchRequestHandler
import com.arvatosystems.t9t.base.search.Description
import de.jpaw.dp.Jdp

class LeanUserSearchRequestHandler extends AbstractLeanSearchRequestHandler<LeanUserSearchRequest, UserEntity> {
    public new() {
        super(Jdp.getRequired(IUserEntityResolver),
            [ return new Description(null, userId, name, false, false) ]
        )
    }
}
