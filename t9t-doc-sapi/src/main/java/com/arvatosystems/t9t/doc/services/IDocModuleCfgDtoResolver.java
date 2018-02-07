package com.arvatosystems.t9t.doc.services;

import com.arvatosystems.t9t.doc.DocModuleCfgDTO;
import com.arvatosystems.t9t.server.services.IModuleConfigResolver;

public interface IDocModuleCfgDtoResolver extends IModuleConfigResolver<DocModuleCfgDTO> {
    public static final DocModuleCfgDTO DEFAULT_MODULE_CFG = new DocModuleCfgDTO(
        null,       // Json z
        true,       // considerGlobalTemplates
        true,       // considerGlobalTexts
        true,       // considerGlobalBinaries
        1_000_000,  // weightTenantMatch
        10_000,     // weightLanguageMatch
        1,          // weightCurrencyMatch
        100,        // weightCountryMatch
        1000        // weightEntityMatch
    );
}
