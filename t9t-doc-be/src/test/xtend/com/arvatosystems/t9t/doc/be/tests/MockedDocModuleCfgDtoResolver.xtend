package com.arvatosystems.t9t.doc.be.tests

import com.arvatosystems.t9t.doc.DocModuleCfgDTO
import com.arvatosystems.t9t.doc.services.IDocModuleCfgDtoResolver
import de.jpaw.dp.Fallback
import de.jpaw.dp.Singleton

@Fallback
@Singleton
class MockedDocModuleCfgDtoResolver implements IDocModuleCfgDtoResolver {

    override getModuleConfiguration() {
        return DEFAULT_MODULE_CFG
    }

    override getDefaultModuleConfiguration() {
        return DEFAULT_MODULE_CFG
    }
    /** Updates module configuration with a new one. Writes to the DB and updates the local cache.
     * Default implementation provided only to simplify creation of mocks.
     * @return
     */
    override updateModuleConfiguration(DocModuleCfgDTO newCfg) {
        throw new UnsupportedOperationException();
    }
}
