package com.arvatosystems.t9t.voice.client

import java.util.Map
import java.util.UUID

class VoiceSessionContext {
    var public String providerSessionKey;
    var public UUID connectionApiKey;
    var public boolean shouldTerminateWhenDone;
    var public String userName;
    var public String userInternalId;
    // var public String connectionAuthentication;  // usually "Bearer " + encodedJwt
    var public Map<String,String> nextCallbacks;    // special response mapping
    var public Map<String,String> previousCallbacks;    // special response mapping before - deleted after intent execution
}
