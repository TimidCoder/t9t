package com.arvatosystems.t9t.voice.services;

import com.arvatosystems.t9t.server.services.IModuleConfigResolver;
import com.arvatosystems.t9t.voice.VoiceModuleCfgDTO;

public interface IVoiceModuleCfgDtoResolver extends IModuleConfigResolver<VoiceModuleCfgDTO> {
    public static final VoiceModuleCfgDTO DEFAULT_MODULE_CFG = new VoiceModuleCfgDTO(
        null       // Json z
    );
}
