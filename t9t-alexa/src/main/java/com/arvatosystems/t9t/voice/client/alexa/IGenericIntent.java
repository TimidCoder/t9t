package com.arvatosystems.t9t.voice.client.alexa;

import com.arvatosystems.t9t.voice.client.VoiceSessionContext;

import de.jpaw.bonaparte.pojos.api.alexa.AlexaIntentIn;
import de.jpaw.bonaparte.pojos.api.alexa.AlexaResponse;

/** Specific implementations are injected via @Named qualifier. */
public interface IGenericIntent<T extends VoiceSessionContext> {
    void execute(T ctx, AlexaIntentIn intent, AlexaResponse resp); // intent = rq.get("type")
}
