package com.arvatosystems.t9t.base.services;

/** Responsible for the mapping of tenant IDs / refs to allow shared use of resources across tenants. */
public interface ITenantMapping {
    public String getSharedTenantId(int rtti);       // the tenantId is provided as a parameter to allow the constant "Nocustomization" object
    public Long getSharedTenantRef(int rtti);       // the tenantRef is provided as a parameter to allow the constant "Nocustomization" object
}
