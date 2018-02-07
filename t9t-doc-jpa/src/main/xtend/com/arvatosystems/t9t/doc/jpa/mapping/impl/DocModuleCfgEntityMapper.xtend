package com.arvatosystems.t9t.doc.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.doc.DocModuleCfgDTO
import com.arvatosystems.t9t.doc.jpa.entities.DocModuleCfgEntity
import com.arvatosystems.t9t.doc.jpa.persistence.IDocModuleCfgEntityResolver

@AutoMap42
class DocModuleCfgEntityMapper {
    IDocModuleCfgEntityResolver entityResolver

    @AutoHandler("SP42")
    def void d2eDocModuleCfgDTO(DocModuleCfgEntity entity, DocModuleCfgDTO dto, boolean onlyActive) {}
    def void e2dDocModuleCfgDTO(DocModuleCfgEntity entity, DocModuleCfgDTO dto) {}
}
