package com.arvatosystems.t9t.auth.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.auth.AuthModuleCfgDTO
import com.arvatosystems.t9t.auth.jpa.entities.AuthModuleCfgEntity
import com.arvatosystems.t9t.auth.jpa.persistence.IAuthModuleCfgEntityResolver

@AutoMap42
class AuthModuleCfgEntityMapper {
    IAuthModuleCfgEntityResolver entityResolver

    @AutoHandler("S42")
    def void d2eAuthModuleCfgDTO(AuthModuleCfgEntity entity, AuthModuleCfgDTO dto, boolean onlyActive) {}
    def void e2dAuthModuleCfgDTO(AuthModuleCfgEntity entity, AuthModuleCfgDTO dto) {}

}
