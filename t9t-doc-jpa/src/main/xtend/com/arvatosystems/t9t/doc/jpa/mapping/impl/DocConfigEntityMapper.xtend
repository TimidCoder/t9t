package com.arvatosystems.t9t.doc.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.doc.DocConfigDTO
import com.arvatosystems.t9t.doc.jpa.entities.DocConfigEntity
import com.arvatosystems.t9t.doc.jpa.persistence.IDocConfigEntityResolver

@AutoMap42
class DocConfigEntityMapper {
    IDocConfigEntityResolver entityResolver

    @AutoHandler("CSP42")
    def void d2eDocConfigDTO(DocConfigEntity entity, DocConfigDTO dto, boolean onlyActive) {}
    def void e2dDocConfigDTO(DocConfigEntity entity, DocConfigDTO dto) {}

}
