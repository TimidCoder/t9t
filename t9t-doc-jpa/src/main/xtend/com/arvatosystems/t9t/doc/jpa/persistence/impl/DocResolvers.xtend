package com.arvatosystems.t9t.doc.jpa.persistence.impl

import com.arvatosystems.t9t.annotations.jpa.AllCanAccessGlobalTenant
import com.arvatosystems.t9t.annotations.jpa.AutoResolver42
import com.arvatosystems.t9t.doc.DocComponentRef
import com.arvatosystems.t9t.doc.DocConfigRef
import com.arvatosystems.t9t.doc.DocEmailCfgRef
import com.arvatosystems.t9t.doc.DocTemplateRef
import com.arvatosystems.t9t.doc.jpa.entities.DocComponentEntity
import com.arvatosystems.t9t.doc.jpa.entities.DocConfigEntity
import com.arvatosystems.t9t.doc.jpa.entities.DocEmailCfgEntity
import com.arvatosystems.t9t.doc.jpa.entities.DocModuleCfgEntity
import com.arvatosystems.t9t.doc.jpa.entities.DocTemplateEntity

/*
 * Generates resolver classes for all entities in the doc module. The generator class itself is not used.
 */
@AutoResolver42
class DocResolvers {

    @AllCanAccessGlobalTenant
    def DocModuleCfgEntity      getDocModuleCfgEntity       (Long key,       boolean onlyActive) {}
    @AllCanAccessGlobalTenant
    def DocConfigEntity         getDocConfigEntity          (DocConfigRef ref,          boolean onlyActive) {}
    @AllCanAccessGlobalTenant
    def DocEmailCfgEntity       getDocEmailCfgEntity        (DocEmailCfgRef ref,        boolean onlyActive) {}
    @AllCanAccessGlobalTenant
    def DocTemplateEntity       getDocTemplateEntity        (DocTemplateRef ref,        boolean onlyActive) {}
    @AllCanAccessGlobalTenant
    def DocComponentEntity      getDocComponentEntity       (DocComponentRef ref,       boolean onlyActive) {}
}
