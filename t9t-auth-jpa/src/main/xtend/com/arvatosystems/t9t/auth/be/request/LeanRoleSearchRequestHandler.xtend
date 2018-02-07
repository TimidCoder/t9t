package com.arvatosystems.t9t.auth.be.request

import com.arvatosystems.t9t.auth.jpa.entities.RoleEntity
import com.arvatosystems.t9t.auth.jpa.persistence.IRoleEntityResolver
import com.arvatosystems.t9t.auth.request.LeanRoleSearchRequest
import com.arvatosystems.t9t.base.jpa.impl.AbstractLeanSearchRequestHandler
import com.arvatosystems.t9t.base.search.Description
import de.jpaw.dp.Jdp

class LeanRoleSearchRequestHandler extends AbstractLeanSearchRequestHandler<LeanRoleSearchRequest, RoleEntity> {
    public new() {
        super(Jdp.getRequired(IRoleEntityResolver),
            [ return new Description(null, roleId, name, false, false) ]
        )
    }
}
