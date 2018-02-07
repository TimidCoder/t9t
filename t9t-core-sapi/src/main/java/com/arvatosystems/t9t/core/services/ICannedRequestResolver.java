package com.arvatosystems.t9t.core.services;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.core.CannedRequestDTO;
import com.arvatosystems.t9t.core.CannedRequestRef;

import de.jpaw.bonaparte.refsw.RefResolver;

/** Implemented via cache. */
public interface ICannedRequestResolver extends RefResolver<CannedRequestRef, CannedRequestDTO, FullTrackingWithVersion> {}
