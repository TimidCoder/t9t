package com.arvatosystems.t9t.auth.services;

import com.arvatosystems.t9t.auth.RoleDTO;
import com.arvatosystems.t9t.auth.RoleRef;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;

import de.jpaw.bonaparte.refsw.RefResolver;

public interface IRoleResolver extends RefResolver<RoleRef, RoleDTO, FullTrackingWithVersion> {}
