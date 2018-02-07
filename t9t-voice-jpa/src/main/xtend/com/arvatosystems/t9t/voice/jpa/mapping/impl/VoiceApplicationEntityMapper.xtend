package com.arvatosystems.t9t.voice.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.voice.VoiceApplicationDTO
import com.arvatosystems.t9t.voice.jpa.entities.VoiceApplicationEntity
import com.arvatosystems.t9t.voice.jpa.persistence.IVoiceApplicationEntityResolver

@AutoMap42
class VoiceApplicationEntityMapper {
    IVoiceApplicationEntityResolver entityResolver

    @AutoHandler("CSP42")
    def void d2eVoiceApplicationDTO(VoiceApplicationEntity entity, VoiceApplicationDTO dto, boolean onlyActive) {}
    def void e2dVoiceApplicationDTO(VoiceApplicationEntity entity, VoiceApplicationDTO dto) {}

}
