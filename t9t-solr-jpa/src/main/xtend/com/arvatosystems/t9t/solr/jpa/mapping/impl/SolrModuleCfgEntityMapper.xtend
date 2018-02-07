package com.arvatosystems.t9t.solr.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.solr.SolrModuleCfgDTO
import com.arvatosystems.t9t.solr.jpa.entities.SolrModuleCfgEntity
import com.arvatosystems.t9t.solr.jpa.persistence.ISolrModuleCfgEntityResolver

@AutoMap42
class SolrModuleCfgEntityMapper {
    ISolrModuleCfgEntityResolver entityResolver

    @AutoHandler("SP42")
    def void d2eSolrModuleCfgDTO(SolrModuleCfgEntity entity, SolrModuleCfgDTO dto, boolean onlyActive) {}
    def void e2dSolrModuleCfgDTO(SolrModuleCfgEntity entity, SolrModuleCfgDTO dto) {}
}
