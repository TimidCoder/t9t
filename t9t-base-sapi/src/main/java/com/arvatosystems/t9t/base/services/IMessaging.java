package com.arvatosystems.t9t.base.services;

import com.arvatosystems.t9t.base.api.RequestParameters;

public interface IMessaging {
    /** Publish a message to some event bus, for any subscriber to receive it. */
    void publishMessage(String topic, Object data);

    /** Send a message to a queue, for a specific receiver to consume it (excatly one receiver). */
    void sendMessage(String queue, Object data);

    /** Execute a command remotely. Uses a mapped userId / tenantId. */
    void executeRemote(String queue, RequestParameters rq);
}
