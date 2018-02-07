package com.arvatosystems.t9t.uiprefsv3.services;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.uiprefsv3.LeanGridConfigDTO;
import com.arvatosystems.t9t.uiprefsv3.LeanGridConfigRef;

import de.jpaw.bonaparte.refsw.RefResolver;

public interface ILeanGridConfigResolver extends RefResolver<LeanGridConfigRef, LeanGridConfigDTO, FullTrackingWithVersion> {}
