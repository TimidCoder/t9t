package com.arvatosystems.t9t.base.services;


public interface ICustomization {
    public ITenantCustomization getTenantCustomization(Long tenantRef, String tenantId);
    public ITenantMapping getTenantMapping(Long tenantRef, String tenantId);
}
