package com.arvatosystems.t9t.voice.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.voice.VoiceApplicationKey
import com.arvatosystems.t9t.voice.VoiceResponseDTO
import com.arvatosystems.t9t.voice.jpa.entities.VoiceResponseEntity
import com.arvatosystems.t9t.voice.jpa.persistence.IVoiceApplicationEntityResolver
import com.arvatosystems.t9t.voice.jpa.persistence.IVoiceResponseEntityResolver

@AutoMap42
class VoiceResponseEntityMapper {
    IVoiceResponseEntityResolver entityResolver
    IVoiceApplicationEntityResolver voiceApplicationResolver

    @AutoHandler("CSP42")
    def void d2eVoiceResponseDTO(VoiceResponseEntity entity, VoiceResponseDTO it, boolean onlyActive) {
        entity.applicationRef = voiceApplicationResolver.getRef(applicationRef, false)
    }
    def void e2dVoiceResponseDTO(VoiceResponseEntity it, VoiceResponseDTO dto) {
        val appl = voiceApplicationResolver.getEntityDataForKey(applicationRef, false)
        dto.applicationRef = new VoiceApplicationKey(appl.objectRef, appl.applicationId)
    }
}
