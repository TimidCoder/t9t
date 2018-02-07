package com.arvatosystems.t9t.doc.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.doc.DocTemplateDTO
import com.arvatosystems.t9t.doc.jpa.entities.DocTemplateEntity
import com.arvatosystems.t9t.doc.jpa.persistence.IDocTemplateEntityResolver

@AutoMap42
class DocTemplateEntityMapper {
    IDocTemplateEntityResolver entityResolver

    @AutoHandler("SP42")
    def void d2eDocTemplateDTO(DocTemplateEntity entity, DocTemplateDTO dto, boolean onlyActive) {}
    def void e2dDocTemplateDTO(DocTemplateEntity entity, DocTemplateDTO dto) {}
}
