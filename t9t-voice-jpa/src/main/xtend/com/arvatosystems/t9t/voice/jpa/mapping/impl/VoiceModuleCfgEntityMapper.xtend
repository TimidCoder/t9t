package com.arvatosystems.t9t.voice.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.voice.VoiceModuleCfgDTO
import com.arvatosystems.t9t.voice.jpa.entities.VoiceModuleCfgEntity
import com.arvatosystems.t9t.voice.jpa.persistence.IVoiceModuleCfgEntityResolver

@AutoMap42
class VoiceModuleCfgEntityMapper {
    IVoiceModuleCfgEntityResolver entityResolver

    @AutoHandler("SP42")
    def void d2eVoiceModuleCfgDTO(VoiceModuleCfgEntity entity, VoiceModuleCfgDTO dto, boolean onlyActive) {}
    def void e2dVoiceModuleCfgDTO(VoiceModuleCfgEntity entity, VoiceModuleCfgDTO dto) {}
}
