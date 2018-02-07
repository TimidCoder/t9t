package com.arvatosystems.t9t.core.jpa.persistence.impl

import com.arvatosystems.t9t.annotations.jpa.AllCanAccessGlobalTenant
import com.arvatosystems.t9t.annotations.jpa.AutoResolver42
import com.arvatosystems.t9t.core.CannedRequestRef
import com.arvatosystems.t9t.core.jpa.entities.CannedRequestEntity

@AutoResolver42
class CoreResolvers {
//
//    def SubscriberConfigEntity    getSubscriberConfigEntity (SubscriberConfigRef    entityRef, boolean onlyActive) { return null; }
//    def InputConfigEntity getInputConfigEntity (ImportRef  entityRef, boolean onlyActive) { return null; }
//
//    @NoAutomaticTenantFilter
//    def TenantMappings       getTenantMappings   (TenantMappingRef    entityRef, boolean onlyActive) { return null; }

    @AllCanAccessGlobalTenant   // must allow read access to global defaults
    def CannedRequestEntity getCannedRequestEntity(CannedRequestRef  entityRef, boolean onlyActive) { return null; }
}
