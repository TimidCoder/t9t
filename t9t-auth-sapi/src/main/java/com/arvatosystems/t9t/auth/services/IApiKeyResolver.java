package com.arvatosystems.t9t.auth.services;

import com.arvatosystems.t9t.auth.ApiKeyDTO;
import com.arvatosystems.t9t.auth.ApiKeyRef;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;

import de.jpaw.bonaparte.refsw.RefResolver;

public interface IApiKeyResolver extends RefResolver<ApiKeyRef, ApiKeyDTO, FullTrackingWithVersion> {}
