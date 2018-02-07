package com.arvatosystems.t9t.base.services;

/** Marker interface for JPA entities, to support tenant operations. */
public interface IsTenantSpecific {
    Long getTenantRef();
    void setTenantRef(Long tenantRef);
}
