package com.arvatosystems.t9t.voice.jpa.impl

import com.arvatosystems.t9t.core.jpa.impl.AbstractModuleConfigResolver
import com.arvatosystems.t9t.voice.VoiceModuleCfgDTO
import com.arvatosystems.t9t.voice.jpa.entities.VoiceModuleCfgEntity
import com.arvatosystems.t9t.voice.jpa.persistence.IVoiceModuleCfgEntityResolver
import com.arvatosystems.t9t.voice.services.IVoiceModuleCfgDtoResolver
import de.jpaw.dp.Singleton

@Singleton
class VoiceModuleCfgDtoResolver extends AbstractModuleConfigResolver<VoiceModuleCfgDTO, VoiceModuleCfgEntity> implements IVoiceModuleCfgDtoResolver {

    public new() {
        super(IVoiceModuleCfgEntityResolver)
    }

    override public VoiceModuleCfgDTO getDefaultModuleConfiguration() {
        return DEFAULT_MODULE_CFG;
    }
}
