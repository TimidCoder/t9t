package com.arvatosystems.t9t.doc.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.doc.DocEmailCfgDTO
import com.arvatosystems.t9t.doc.jpa.entities.DocEmailCfgEntity
import com.arvatosystems.t9t.doc.jpa.persistence.IDocEmailCfgEntityResolver

@AutoMap42
class DocEmailCfgEntityMapper {
    IDocEmailCfgEntityResolver entityResolver

    @AutoHandler("SP42")
    def void d2eDocEmailCfgDTO(DocEmailCfgEntity entity, DocEmailCfgDTO dto, boolean onlyActive) {}
    def void e2dDocEmailCfgDTO(DocEmailCfgEntity entity, DocEmailCfgDTO dto) {}

}
