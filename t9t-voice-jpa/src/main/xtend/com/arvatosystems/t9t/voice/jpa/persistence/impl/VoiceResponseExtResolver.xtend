package com.arvatosystems.t9t.voice.jpa.persistence.impl

import com.arvatosystems.t9t.voice.VoiceResponseInternalKey
import com.arvatosystems.t9t.voice.VoiceResponseKey
import com.arvatosystems.t9t.voice.VoiceResponseRef
import com.arvatosystems.t9t.voice.jpa.persistence.IVoiceApplicationEntityResolver
import de.jpaw.dp.Inject
import de.jpaw.dp.Singleton
import de.jpaw.dp.Specializes

@Singleton
@Specializes
class VoiceResponseExtResolver extends VoiceResponseEntityResolver {

    @Inject IVoiceApplicationEntityResolver applicationResolver;

    override protected VoiceResponseRef resolveNestedRefs(VoiceResponseRef ref) {
        if (ref instanceof VoiceResponseKey) {
            return new VoiceResponseInternalKey => [
                applicationRef = applicationResolver.getRef(ref.applicationRef, false)
                languageCode   = ref.languageCode
                key            = ref.key
            ]
        }
        return super.resolveNestedRefs(ref);
    }
}
