package com.arvatosystems.t9t.base.services;

import com.arvatosystems.t9t.base.api.ServiceRequest;
import com.arvatosystems.t9t.base.event.EventData;

/** The IAsyncRequestProcessor allows the asynchronous (in a separate transaction) processing of requests.
 * It is guaranteed that requests will be started in the order of submission.
 * They will not necessarily be completed in this order. If one request should be requests in a common transaction,
 * the BatchRequest is suitable for that. If they should be decoupled into separate transactions, use the AsyncBatchRequest,
 * which processes the second one after the first one has been completed, but in a separate transaction.
 *
 * The ServiceRequest has to include AuthenticationParameters (currently only JWT is supported).
 */
public interface IAsyncRequestProcessor {
    /** Executes a task asynchronously. To be called from externally of any request context. */
    void submitTask(ServiceRequest request);

    /** Sends event data to a single subscriber (node). */
    void send(EventData data);

    /** Publishes event data to all subscribers. */
    void publish(EventData data);

    /** Register an IEventHandler as subscriber for an eventID. */
    void registerSubscriber(String eventID, IEventHandler subscriber);
}
