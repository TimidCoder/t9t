package com.arvatosystems.t9t.base.be.stubs;

import com.arvatosystems.t9t.base.services.ITenantMapping;

public class NoTenantMapping implements ITenantMapping {

    final Long tenantRef;
    final String tenantId;

    public NoTenantMapping(Long tenantRef, String tenantId) {
        this.tenantRef = tenantRef;
        this.tenantId = tenantId;
    }

    @Override
    public Long getSharedTenantRef(int rtti) {
        return tenantRef;
    }

    @Override
    public String getSharedTenantId(int rtti) {
        return tenantId;
    }
}
