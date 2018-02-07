package com.arvatosystems.t9t.auth.services;

import com.arvatosystems.t9t.auth.UserDTO;
import com.arvatosystems.t9t.auth.UserRef;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;

import de.jpaw.bonaparte.refsw.RefResolver;

/** Implemented via cache. */
public interface IUserResolver extends RefResolver<UserRef, UserDTO, FullTrackingWithVersion> {}
