package com.arvatosystems.t9t.doc.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.doc.DocComponentDTO
import com.arvatosystems.t9t.doc.jpa.entities.DocComponentEntity
import com.arvatosystems.t9t.doc.jpa.persistence.IDocComponentEntityResolver

@AutoMap42
class DocComponentEntityMapper {
    IDocComponentEntityResolver entityResolver

    def void d2eDocComponentDTO(DocComponentEntity entity, DocComponentDTO dto, boolean onlyActive) {}

    @AutoHandler("SP42")
    def void e2dDocComponentDTO(DocComponentEntity entity, DocComponentDTO dto) {
    }

}
