package com.arvatosystems.t9t.email.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.email.EmailDTO
import com.arvatosystems.t9t.email.jpa.entities.EmailEntity
import com.arvatosystems.t9t.email.jpa.persistence.IEmailEntityResolver

@AutoMap42
class EmailEntityMapper {
    IEmailEntityResolver entityResolver

    @AutoHandler("SC42")
    def void d2eEmailDTO(EmailEntity entity, EmailDTO dto, boolean onlyActive) {}
    def void e2dEmailDTO(EmailEntity entity, EmailDTO dto) {}
}
