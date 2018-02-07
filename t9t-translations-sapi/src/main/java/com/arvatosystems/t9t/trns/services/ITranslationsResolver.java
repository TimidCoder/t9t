package com.arvatosystems.t9t.trns.services;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.trns.TranslationsDTO;
import com.arvatosystems.t9t.trns.TranslationsRef;

import de.jpaw.bonaparte.refsw.RefResolver;

public interface ITranslationsResolver extends RefResolver<TranslationsRef, TranslationsDTO, FullTrackingWithVersion> {}
