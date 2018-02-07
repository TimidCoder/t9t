package com.arvatosystems.t9t.trns.jpa.persistence.impl

import com.arvatosystems.t9t.annotations.jpa.AllCanAccessGlobalTenant
import com.arvatosystems.t9t.annotations.jpa.AutoResolver42
import com.arvatosystems.t9t.trns.TranslationsRef
import com.arvatosystems.t9t.trns.jpa.entities.TranslationsEntity
import com.arvatosystems.t9t.trns.jpa.entities.TrnsModuleCfgEntity

@AutoResolver42
class TrnsResolvers {
    @AllCanAccessGlobalTenant
    def TrnsModuleCfgEntity     getTrnsModuleCfgEntity      (Long key,      boolean onlyActive) {}
    @AllCanAccessGlobalTenant
    def TranslationsEntity      getTranslationsEntity       (TranslationsRef ref,       boolean onlyActive) {}
}
