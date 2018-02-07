package com.arvatosystems.t9t.auth.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.auth.TenantLogoDTO
import com.arvatosystems.t9t.auth.jpa.entities.TenantLogoEntity
import com.arvatosystems.t9t.auth.jpa.persistence.ITenantLogoEntityResolver

@AutoMap42
class TenantLogoEntityMapper {
    ITenantLogoEntityResolver entityResolver

    @AutoHandler("S42")
    def void d2eTenantLogoDTO(TenantLogoEntity entity, TenantLogoDTO dto, boolean onlyActive) {}
    def void e2dTenantLogoDTO(TenantLogoEntity entity, TenantLogoDTO dto) {}
}
