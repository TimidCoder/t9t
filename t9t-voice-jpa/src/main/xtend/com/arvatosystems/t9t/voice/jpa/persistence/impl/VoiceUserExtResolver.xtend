package com.arvatosystems.t9t.voice.jpa.persistence.impl

import com.arvatosystems.t9t.voice.VoiceUserInternalKey
import com.arvatosystems.t9t.voice.VoiceUserKey
import com.arvatosystems.t9t.voice.VoiceUserRef
import com.arvatosystems.t9t.voice.jpa.persistence.IVoiceApplicationEntityResolver
import de.jpaw.dp.Inject
import de.jpaw.dp.Singleton
import de.jpaw.dp.Specializes

@Singleton
@Specializes
class VoiceUserExtResolver extends VoiceUserEntityResolver {

    @Inject IVoiceApplicationEntityResolver applicationResolver;

    override protected VoiceUserRef resolveNestedRefs(VoiceUserRef ref) {
        if (ref instanceof VoiceUserKey) {
            return new VoiceUserInternalKey => [
                applicationRef = applicationResolver.getRef(ref.applicationRef, false)
                providerId     = ref.providerId
            ]
        }
        return super.resolveNestedRefs(ref);
    }
}
