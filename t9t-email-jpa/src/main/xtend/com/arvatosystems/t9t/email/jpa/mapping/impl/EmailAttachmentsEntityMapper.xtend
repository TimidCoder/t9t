package com.arvatosystems.t9t.email.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.email.EmailAttachmentsDTO
import com.arvatosystems.t9t.email.jpa.entities.EmailAttachmentsEntity
import com.arvatosystems.t9t.email.jpa.persistence.IEmailAttachmentsEntityResolver

@AutoMap42
class EmailAttachmentsEntityMapper {
    IEmailAttachmentsEntityResolver entityResolver

    @AutoHandler("S42")
    def void d2eEmailAttachmentsDTO(EmailAttachmentsEntity entity, EmailAttachmentsDTO dto, boolean onlyActive) {}
    def void e2dEmailAttachmentsDTO(EmailAttachmentsEntity entity, EmailAttachmentsDTO dto) {}
}
