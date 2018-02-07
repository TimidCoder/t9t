package com.arvatosystems.t9t.batch.services;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.batch.SliceTrackingDTO;
import com.arvatosystems.t9t.batch.SliceTrackingRef;

import de.jpaw.bonaparte.refsw.RefResolver;

public interface ISliceTrackingResolver extends RefResolver<SliceTrackingRef, SliceTrackingDTO, FullTrackingWithVersion> {}
