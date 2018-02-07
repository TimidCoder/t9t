package com.arvatosystems.t9t.solr.jpa.persistence.impl

import com.arvatosystems.t9t.annotations.jpa.AllCanAccessGlobalTenant
import com.arvatosystems.t9t.annotations.jpa.AutoResolver42
import com.arvatosystems.t9t.solr.jpa.entities.SolrModuleCfgEntity

/*
 * Generates resolver classes for all entities in the SOLR module. The generator class itself is not used.
 */
@AutoResolver42
class SolrResolvers {

    @AllCanAccessGlobalTenant
    def SolrModuleCfgEntity     getSolrModuleCfgEntity      (Long key,       boolean onlyActive) {}
}
