package com.arvatosystems.t9t.auth.services;

import com.arvatosystems.t9t.auth.TenantDTO;
import com.arvatosystems.t9t.auth.TenantRef;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;

import de.jpaw.bonaparte.refsw.RefResolver;

/** Implemented via cache. */
public interface ITenantResolver extends RefResolver<TenantRef, TenantDTO, FullTrackingWithVersion> {}
