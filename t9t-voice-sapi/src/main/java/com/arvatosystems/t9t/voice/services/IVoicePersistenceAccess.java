package com.arvatosystems.t9t.voice.services;

import com.arvatosystems.t9t.voice.VoiceUserDTO;

/** Defines the communication layer between the backend modules (business logic / persistence layer). */
public interface IVoicePersistenceAccess {
    VoiceUserDTO getUserForExternalId(Long tenantRef, Long applicationRef, String providerId);
}
