package com.arvatosystems.t9t.auth.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.annotations.jpa.NeedMapping
import com.arvatosystems.t9t.auth.SessionDTO
import com.arvatosystems.t9t.auth.TenantKey
import com.arvatosystems.t9t.auth.UserKey
import com.arvatosystems.t9t.auth.jpa.entities.SessionEntity
import com.arvatosystems.t9t.auth.jpa.persistence.ISessionEntityResolver
import com.arvatosystems.t9t.auth.jpa.persistence.ITenantEntityResolver
import com.arvatosystems.t9t.auth.jpa.persistence.IUserEntityResolver

@AutoMap42
public class SessionMappers {
    ISessionEntityResolver resolver
    ITenantEntityResolver  tenantResolver
    IUserEntityResolver    userResolver

    @AutoHandler("S42")
    @NeedMapping
    def void e2dSessionDTO(SessionEntity it, SessionDTO dto) {
        val tenant    = tenantResolver.find(tenantRef)
        val user      = userResolver.find(userRef)
        dto.userRef   = new UserKey(userRef, user?.userId ?: '?')
        dto.tenantRef = new TenantKey(tenantRef, tenant?.tenantId ?: '?')
    }
}
