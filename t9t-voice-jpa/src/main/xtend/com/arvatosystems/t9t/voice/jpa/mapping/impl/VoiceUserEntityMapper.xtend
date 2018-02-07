package com.arvatosystems.t9t.voice.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.voice.VoiceApplicationKey
import com.arvatosystems.t9t.voice.VoiceUserDTO
import com.arvatosystems.t9t.voice.jpa.entities.VoiceUserEntity
import com.arvatosystems.t9t.voice.jpa.persistence.IVoiceApplicationEntityResolver
import com.arvatosystems.t9t.voice.jpa.persistence.IVoiceUserEntityResolver

@AutoMap42
class VoiceUserEntityMapper {
    IVoiceUserEntityResolver entityResolver
    IVoiceApplicationEntityResolver voiceApplicationResolver

    @AutoHandler("SP42")
    def void d2eVoiceUserDTO(VoiceUserEntity entity, VoiceUserDTO it, boolean onlyActive) {
        entity.applicationRef = voiceApplicationResolver.getRef(applicationRef, false)
    }
    def void e2dVoiceUserDTO(VoiceUserEntity it, VoiceUserDTO dto) {
        val appl = voiceApplicationResolver.getEntityDataForKey(applicationRef, false)
        dto.applicationRef = new VoiceApplicationKey(appl.objectRef, appl.applicationId)
    }
}
