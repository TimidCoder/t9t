package com.arvatosystems.t9t.email.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.email.EmailModuleCfgDTO
import com.arvatosystems.t9t.email.jpa.entities.EmailModuleCfgEntity
import com.arvatosystems.t9t.email.jpa.persistence.IEmailModuleCfgEntityResolver

@AutoMap42
class EmailModuleCfgEntityMapper {
    IEmailModuleCfgEntityResolver entityResolver

    @AutoHandler("SP42")
    def void d2eEmailModuleCfgDTO(EmailModuleCfgEntity entity, EmailModuleCfgDTO dto, boolean onlyActive) {}
    def void e2dEmailModuleCfgDTO(EmailModuleCfgEntity entity, EmailModuleCfgDTO dto) {}

}
