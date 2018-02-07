package com.arvatosystems.t9t.base.vertx.impl

import com.arvatosystems.t9t.base.api.ServiceRequest
import com.arvatosystems.t9t.base.api.ServiceResponse
import com.arvatosystems.t9t.base.event.EventData
import com.arvatosystems.t9t.base.event.EventParameters
import com.arvatosystems.t9t.base.event.GenericEvent
import com.arvatosystems.t9t.base.request.ProcessEventRequest
import com.arvatosystems.t9t.base.services.IAsyncRequestProcessor
import com.arvatosystems.t9t.base.services.IEventHandler
import com.arvatosystems.t9t.base.services.impl.EventSubscriptionCache
import com.arvatosystems.t9t.base.types.AuthenticationJwt
import com.arvatosystems.t9t.server.services.IUnauthenticatedServiceRequestExecutor
import de.jpaw.annotations.AddLogger
import de.jpaw.bonaparte.core.BonaPortable
import de.jpaw.bonaparte.core.JsonComposer
import de.jpaw.bonaparte.core.MapComposer
import de.jpaw.bonaparte.core.MapParser
import de.jpaw.bonaparte8.vertx3.CompactMessageCodec
import de.jpaw.dp.Default
import de.jpaw.dp.Jdp
import de.jpaw.dp.Named
import de.jpaw.dp.Singleton
import de.jpaw.json.JsonParser
import de.jpaw.util.ApplicationException
import io.vertx.core.Vertx
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.JsonObject
import java.util.HashSet
import java.util.Set
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import com.arvatosystems.t9t.cfg.be.ConfigProvider
import io.vertx.core.WorkerExecutor

@AddLogger
@Singleton
@Default
class AsyncProcessor implements IAsyncRequestProcessor {
    private static final String ASYNC_EVENTBUS_ADDRESS = "t9tasync"
    private static final String EVENTBUS_BASE_42 = "event42.";
    private static EventBus bus = null;
    private static Vertx myVertx = null;
    private static final ConcurrentMap<String, Set<IEventHandler>> SUBSCRIBERS = new ConcurrentHashMap<String, Set<IEventHandler>>();

    private static IUnauthenticatedServiceRequestExecutor serviceRequestExecutor
    private static WorkerExecutor asyncExecutorPool = null  // possibly assigned during register()

    new() {
        LOGGER.info("vert.x AsyncProcessor implementation selected - async commands can be sent across JVMs");
    }

    def private static String toBusAddress(String eventID) {
        return EVENTBUS_BASE_42 + eventID
    }

    /** Called only if the bus has been set up before, to register a newly provided subscriber */
    def private static void addConsumer(String eventID, IEventHandler subscriber) {
        // get the qualifier of the subscriber
        val qualifier = subscriber.class.getAnnotation(Named)?.value
        val consumer = bus.consumer(eventID.toBusAddress)
        consumer.completionHandler [
            if (succeeded)
                LOGGER.info(
                    "vertx async event42 handler {} (qualifier {}) successfully registered on eventbus address {}",
                    subscriber.class.simpleName,
                    qualifier,
                    eventID.toBusAddress
                )
            else
                LOGGER.error("vertx async event42 handler FAILED to register on event bus at {}", eventID.toBusAddress)
        ]
        consumer.handler [
            var JsonObject msgBody
            // first make sure we have a jsonBody to work with. Otherwise any parsing or handling will likely fail
            if (body instanceof String) {
                msgBody = new JsonObject(body as String)
            } else {
                msgBody = body as JsonObject
            }
            // then continue evaluating the message
            if (msgBody instanceof JsonObject) {
                if (msgBody.containsKey("@PQON")) { // check if the event contains custom eventParameters by searching for a PQON
                    var map = new JsonParser(msgBody.encode, false).parseObject
                    val BonaPortable tentativeEventData = MapParser.asBonaPortable(map, MapParser.OUTER_BONAPORTABLE_FOR_JSON)
                    if (tentativeEventData instanceof EventData) {
                        LOGGER.debug("Event {} will now trigger a ProcessEventRequest", tentativeEventData.data.ret$PQON)
                        executeEvent(qualifier, new AuthenticationJwt(tentativeEventData.header.encodedJwt), tentativeEventData.data)
                    }
                } else { // otherwise deal with a raw json event without PQON
                    val theEventID = msgBody.getString("eventID")
                    val header = msgBody.getJsonObject("header")
                    val tenantRef = header?.getLong("tenantRef")
                    val z = msgBody.getJsonObject("z")
                    if (theEventID === null || header === null || tenantRef === null) {
                        LOGGER.error("eventID or header or tenantId missing in event42 object at address {}", eventID)
                    } else if (eventID != theEventID) {
                        LOGGER.error("eventID of received message differs: {} != {}", eventID, theEventID)
                    } else {
                        if (!EventSubscriptionCache.isActive(eventID, qualifier, tenantRef)) {
                            LOGGER.debug("Skipping event {} for tenant {} (not configured)", eventID, tenantRef)
                        } else {
                            LOGGER.debug("Processing an async42 event request {}", theEventID)
                            // pre checks good. construct a request for it
                            executeEvent(qualifier, new AuthenticationJwt(header.getString("encodedJwt")), new GenericEvent => [
                                    it.eventID = eventID
                                    it.z = z?.map
                                    ])
                        }
                    }
                }
            } else {
                LOGGER.error("Received an async message of type {}, cannot handle!", msgBody.class.canonicalName)
            }
        ]

    }

    def private static void executeEvent(String qualifier, AuthenticationJwt authenticationJwt, EventParameters eventParams) {
        val rq = new ProcessEventRequest => [
            eventHandlerQualifier = qualifier
            eventData = eventParams
        ]
        val srq = new ServiceRequest => [
            requestParameters = rq
            authentication = authenticationJwt
        ]
        myVertx.runInWorkerThread(srq)
    }

    /** Registers an implementation of an event handler for a given ID. */
    override registerSubscriber(String eventID, IEventHandler subscriber) {

        LOGGER.debug("Registering subscriber {} for event {} ...", subscriber, eventID);

        var isNewSubscriber = true

        var currentEventHandler = SUBSCRIBERS.get(eventID)

        if (currentEventHandler === null) {
            var newSubscriberSet = new HashSet()
            newSubscriberSet.add(subscriber)
            SUBSCRIBERS.put(eventID, newSubscriberSet)
        } else if (!currentEventHandler.contains(subscriber)) {
            currentEventHandler.add(subscriber)
            SUBSCRIBERS.put(eventID, currentEventHandler)
        } else {
            isNewSubscriber = false
            LOGGER.info("Subscriber {} already registered for event {}. Skip this one.", subscriber, eventID)
        }

        if (isNewSubscriber && bus !== null) {
            addConsumer(eventID, subscriber)
        }
    }

    def public static runInWorkerThread(Vertx vertx, ServiceRequest msgBody) {
        if (asyncExecutorPool !== null && Boolean.TRUE != msgBody.requestHeader?.priorityRequest) {
            asyncExecutorPool.executeBlocking([
                complete(serviceRequestExecutor.executeTrusted(msgBody))
            ], false, [
                if (succeeded) {
                    if (!ApplicationException.isOk(result.returnCode)) {
                        LOGGER.error("Async request {} FAILED with return code {}, {} ({})", msgBody.ret$PQON,
                            result.returnCode, result.errorDetails, result.errorMessage)
                    }
                } else {
                    LOGGER.error("Async request {} FAILED (worker thread terminated abnormally))", msgBody.ret$PQON)
                }
            ])
        } else {
            // no separate pool configured, or priority request
            vertx.<ServiceResponse>executeBlocking([
                complete(serviceRequestExecutor.executeTrusted(msgBody))
            ], false, [
                if (succeeded) {
                    if (!ApplicationException.isOk(result.returnCode)) {
                        LOGGER.error("Async request {} FAILED with return code {}, {} ({})", msgBody.ret$PQON,
                            result.returnCode, result.errorDetails, result.errorMessage)
                    }
                } else {
                    LOGGER.error("Async request {} FAILED (worker thread terminated abnormally))", msgBody.ret$PQON)
                }
            ])
        }
    }

    def public static register(Vertx vertx) {
        myVertx = vertx
        bus = vertx.eventBus
        bus.registerCodec(new CompactMessageCodec)
        serviceRequestExecutor = Jdp.getRequired(IUnauthenticatedServiceRequestExecutor)

        val asyncPoolSize = ConfigProvider.configuration.applicationConfiguration?.localAsyncPoolSize
        if (asyncPoolSize === null) {
            LOGGER.info("Sharing executor pool with sync requests")
        } else {
            LOGGER.info("Using separate executor pool of {} threads for async requests", asyncPoolSize)
            asyncExecutorPool = vertx.createSharedWorkerExecutor("t9t-async-worker", asyncPoolSize);
        }

        val consumer = bus.consumer(AsyncProcessor.ASYNC_EVENTBUS_ADDRESS)
        consumer.completionHandler [
            if (succeeded)
                LOGGER.info("vertx async request processor successfully registered on eventbus address {}",
                    ASYNC_EVENTBUS_ADDRESS)
            else
                LOGGER.error("vertx async request processor FAILED")
        ]
        consumer.handler [
            val msgBody = body
            if (msgBody instanceof ServiceRequest) {
                LOGGER.debug("Processing an async request {}", msgBody.ret$PQON)
                vertx.runInWorkerThread(msgBody)
            } else {
                LOGGER.error("Received an async message of type {}, cannot handle!", msgBody.class.canonicalName)
            }
        ]

        // now also register any preregistered event handlers
        for (q : SUBSCRIBERS.entrySet) {
            for (IEventHandler eh : q.value) {
                addConsumer(q.key, eh)
            }
        }
    }

    // set a long enough timeout (30 minutes) to allow for concurrent batches
    val ASYNC_EVENTBUS_DELIVERY_OPTIONS = (new DeliveryOptions).addHeader("publish", "true").setSendTimeout(30 * 60 *
        1000L).setCodecName(CompactMessageCodec.COMPACT_MESSAGE_CODEC_ID)
    val PUBLISH_EVENTBUS_DELIVERY_OPTIONS = (new DeliveryOptions).addHeader("publish", "true").setSendTimeout(30 * 60 *
        1000L)

    override submitTask(ServiceRequest request) {
        LOGGER.debug("async request {} submitted via vert.x EventBus", request.requestParameters.ret$PQON)
        request.freeze // async must freeze it to avoid subsequent modification
        if (bus !== null)
            bus.send(ASYNC_EVENTBUS_ADDRESS, request, ASYNC_EVENTBUS_DELIVERY_OPTIONS)
    }

    /** Sends event data to a single subscriber. */
    override send(EventData data) {
        LOGGER.debug("async event {} sent via vert.x EventBus", data.data.ret$PQON)
//        data.freeze
        if (bus !== null) {
            val attribs = data.data
            if (attribs instanceof GenericEvent) {
                val payload = new JsonObject();
                payload.put("eventID", attribs.eventID)
                payload.put("header", new JsonObject(MapComposer.marshal(data.header)))
                if (attribs.z !== null) payload.put("z", new JsonObject(attribs.z))
                bus.send(attribs.eventID.toBusAddress, payload.toString)
            } else {
                bus.send(attribs.ret$PQON.toBusAddress, JsonComposer.toJsonString(data), PUBLISH_EVENTBUS_DELIVERY_OPTIONS) // using json for publishing eventData
            }
        } else {
            LOGGER.error("event bus is null - discarding event {}", data.data.ret$PQON)
        }
    }

    /** Publishes event data to all subscribers. */
    override publish(EventData data) {
        LOGGER.debug("async event {} published via vert.x EventBus", data.data.ret$PQON)
//        data.freeze
        if (bus !== null) {
            val attribs = data.data
            if (attribs instanceof GenericEvent) {
                val payload = new JsonObject();
                payload.put("eventID", attribs.eventID)
                payload.put("header", new JsonObject(MapComposer.marshal(data.header)))
                if (attribs.z !== null) payload.put("z", new JsonObject(attribs.z))
                bus.publish(attribs.eventID.toBusAddress, payload.toString)
            } else {
                bus.publish(attribs.ret$PQON.toBusAddress, JsonComposer.toJsonString(data), PUBLISH_EVENTBUS_DELIVERY_OPTIONS) // using json for publishing eventData
            }
        } else {
            LOGGER.error("event bus is null - discarding event {}", data.data.ret$PQON)
        }
    }
}
