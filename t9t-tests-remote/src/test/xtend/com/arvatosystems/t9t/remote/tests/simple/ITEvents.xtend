package com.arvatosystems.t9t.remote.tests.simple

import com.arvatosystems.t9t.base.event.GenericEvent
import com.arvatosystems.t9t.base.request.ProcessEventRequest
import com.arvatosystems.t9t.base.request.PublishEventRequest
import com.arvatosystems.t9t.event.SubscriberConfigDTO
import com.arvatosystems.t9t.remote.connect.Connection
import org.junit.Test

import static extension com.arvatosystems.t9t.misc.extensions.MiscExtensions.*

class ITEvents {
    @Test
    def public void processLoggerEventTest() {
        val dlg = new Connection

        dlg.okIO(new ProcessEventRequest => [
            eventHandlerQualifier  = "logger"
            eventData              = new GenericEvent => [
                eventID            = "originalEventID"
                z                  = #{ "near" -> 1.2, "far" -> "galaxy", "condition" -> true }
            ]
        ])
    }

    @Test
    def public void configureSubscriptionTest() {
        val dlg = new Connection

        new SubscriberConfigDTO => [
            isActive            = true
            eventID             = "originalEventID"
            handlerClassName    = "logger"
            merge(dlg)
        ]
    }

    @Test
    def public void fireSomeCaughtEventTest() {
        val dlg = new Connection

        dlg.okIO(new PublishEventRequest => [
            eventData              = new GenericEvent => [
                eventID            = "originalEventID"
                z                  = #{ "near" -> 1.2, "far" -> "galaxy", "condition" -> true }
            ]
        ])
    }

    @Test
    def public void fireSomeUncaughtEventTest() {
        val dlg = new Connection

        dlg.okIO(new PublishEventRequest => [
            eventData              = new GenericEvent => [
                eventID            = "unknownEventID"
                z                  = #{ "near" -> 1.2, "far" -> "galaxy", "condition" -> true }
            ]
        ])
    }
}
