package com.arvatosystems.t9t.solr.jpa.impl

import com.arvatosystems.t9t.core.jpa.impl.AbstractModuleConfigResolver
import com.arvatosystems.t9t.solr.SolrModuleCfgDTO
import com.arvatosystems.t9t.solr.jpa.entities.SolrModuleCfgEntity
import com.arvatosystems.t9t.solr.jpa.persistence.ISolrModuleCfgEntityResolver
import com.arvatosystems.t9t.solr.services.ISolrModuleCfgDtoResolver
import de.jpaw.dp.Singleton

@Singleton
class SolrModuleCfgDtoResolver extends AbstractModuleConfigResolver<SolrModuleCfgDTO, SolrModuleCfgEntity> implements ISolrModuleCfgDtoResolver {
    private static final SolrModuleCfgDTO DEFAULT_MODULE_CFG = new SolrModuleCfgDTO(
        null,                       // Json z
        null,                       // baseUrl
        null,                       // smtpServerUserId;
        null,                       // smtpServerPassword
        null                        // smtpServerTls
    );

    public new() {
        super(ISolrModuleCfgEntityResolver)
    }

    override public SolrModuleCfgDTO getDefaultModuleConfiguration() {
        return DEFAULT_MODULE_CFG;
    }
}
