package com.arvatosystems.t9t.voice.be.request

import com.arvatosystems.t9t.base.jpa.impl.AbstractLeanSearchRequestHandler
import com.arvatosystems.t9t.base.search.Description
import com.arvatosystems.t9t.voice.jpa.entities.VoiceApplicationEntity
import com.arvatosystems.t9t.voice.jpa.persistence.IVoiceApplicationEntityResolver
import com.arvatosystems.t9t.voice.request.LeanVoiceApplicationSearchRequest
import de.jpaw.dp.Jdp

class LeanVoiceApplicationSearchRequestHandler extends AbstractLeanSearchRequestHandler<LeanVoiceApplicationSearchRequest, VoiceApplicationEntity> {
    public new() {
        super(Jdp.getRequired(IVoiceApplicationEntityResolver),
            [ return new Description(null, applicationId, name, false, false) ]
        )
    }
}
