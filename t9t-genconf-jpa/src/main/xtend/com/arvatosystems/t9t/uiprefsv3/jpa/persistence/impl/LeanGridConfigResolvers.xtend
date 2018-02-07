package com.arvatosystems.t9t.uiprefsv3.jpa.persistence.impl

import com.arvatosystems.t9t.annotations.jpa.AllCanAccessGlobalTenant
import com.arvatosystems.t9t.annotations.jpa.AutoResolver42
import com.arvatosystems.t9t.uiprefsv3.LeanGridConfigRef
import com.arvatosystems.t9t.uiprefsv3.jpa.entities.LeanGridConfigEntity

@AutoResolver42
class LeanGridConfigResolvers {

    @AllCanAccessGlobalTenant  // for DataSinkEntity, everyone can see the global tenant's defaults
    def LeanGridConfigEntity   getLeanGridConfigEntity (LeanGridConfigRef  entityRef, boolean onlyActive) { return null; }
    def LeanGridConfigEntity   findByKey(boolean onlyActive, Long tenantRef, String gridId, Integer variant, Long userRef) { return null; }
}
