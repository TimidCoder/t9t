package com.arvatosystems.t9t.auth.jpa.impl

import com.arvatosystems.t9t.base.T9tConstants
import org.eclipse.xtend.lib.annotations.Data

@Data
class TwoTenantRefs implements T9tConstants {
    Long tenantRef1;
    Long tenantRef2;

    def isDoubleGlobal() {
        return GLOBAL_TENANT_REF42 == tenantRef1 && GLOBAL_TENANT_REF42 == tenantRef2
    }
    def effectiveTenantRef() {
        return if (GLOBAL_TENANT_REF42 == tenantRef1) tenantRef2 else tenantRef1
    }
    override toString() {
        return '''(«tenantRef1», «tenantRef2»)'''
    }
}
