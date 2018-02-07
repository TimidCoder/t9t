package com.arvatosystems.t9t.doc.jpa.impl

import com.arvatosystems.t9t.core.jpa.impl.AbstractModuleConfigResolver
import com.arvatosystems.t9t.doc.DocModuleCfgDTO
import com.arvatosystems.t9t.doc.jpa.entities.DocModuleCfgEntity
import com.arvatosystems.t9t.doc.jpa.persistence.IDocModuleCfgEntityResolver
import com.arvatosystems.t9t.doc.services.IDocModuleCfgDtoResolver
import de.jpaw.dp.Singleton

@Singleton
class DocModuleCfgDtoResolver extends AbstractModuleConfigResolver<DocModuleCfgDTO, DocModuleCfgEntity> implements IDocModuleCfgDtoResolver {

    public new() {
        super(IDocModuleCfgEntityResolver)
    }

    override public DocModuleCfgDTO getDefaultModuleConfiguration() {
        return DEFAULT_MODULE_CFG;
    }
}
