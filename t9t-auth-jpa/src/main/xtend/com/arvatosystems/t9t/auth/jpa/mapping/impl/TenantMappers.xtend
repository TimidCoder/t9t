package com.arvatosystems.t9t.auth.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.auth.TenantDTO
import com.arvatosystems.t9t.auth.jpa.entities.TenantEntity
import com.arvatosystems.t9t.auth.jpa.persistence.ITenantEntityResolver

@AutoMap42
public class TenantMappers {
    ITenantEntityResolver resolver
//    @AutoHandler("S42")   uses the sapi BE search
    def void e2dTenantDTO(TenantEntity entity, TenantDTO dto) {}
}
