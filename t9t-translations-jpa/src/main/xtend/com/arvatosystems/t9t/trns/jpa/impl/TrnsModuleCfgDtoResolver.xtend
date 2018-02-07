package com.arvatosystems.t9t.trns.jpa.impl

import com.arvatosystems.t9t.core.jpa.impl.AbstractModuleConfigResolver
import com.arvatosystems.t9t.trns.TrnsModuleCfgDTO
import com.arvatosystems.t9t.trns.jpa.entities.TrnsModuleCfgEntity
import com.arvatosystems.t9t.trns.jpa.persistence.ITrnsModuleCfgEntityResolver
import com.arvatosystems.t9t.trns.services.ITrnsModuleCfgDtoResolver
import de.jpaw.dp.Singleton

@Singleton
class TrnsModuleCfgDtoResolver extends AbstractModuleConfigResolver<TrnsModuleCfgDTO, TrnsModuleCfgEntity> implements ITrnsModuleCfgDtoResolver {
    private static final TrnsModuleCfgDTO DEFAULT_MODULE_CFG = new TrnsModuleCfgDTO(
        null,       // Json z
        false,      // attemptLocalTenant
        true        // attemptDialects
    )

    public new() {
        super(ITrnsModuleCfgEntityResolver)
    }

    override public TrnsModuleCfgDTO getDefaultModuleConfiguration() {
        return DEFAULT_MODULE_CFG;
    }
}
