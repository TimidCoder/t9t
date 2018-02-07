package com.arvatosystems.t9t.voice.client.alexa

import com.arvatosystems.t9t.base.api.RequestParameters
import com.arvatosystems.t9t.base.api.ServiceResponse
import com.arvatosystems.t9t.voice.client.IBackendCaller
import com.arvatosystems.t9t.voice.client.VoiceSessionContext
import de.jpaw.bonaparte.pojos.api.alexa.AlexaIntentIn
import de.jpaw.dp.Inject

/** Abstract intent, for all implementations.
 * There should be one abstract implementation per application which defines the generic parameter.
 */
abstract class AbstractGenericIntent<T extends VoiceSessionContext> {
    @Inject protected IBackendCaller backendCaller

    def protected ServiceResponse performBackendCall(T ctx, RequestParameters rp) {
        return backendCaller.executeWithApiKey(ctx.connectionApiKey, rp)
    }

    def protected String getSlotValue(AlexaIntentIn intent, String key) {
        if (intent.slots === null)
            return null;
        return intent.slots.get(key)?.value
    }
}
