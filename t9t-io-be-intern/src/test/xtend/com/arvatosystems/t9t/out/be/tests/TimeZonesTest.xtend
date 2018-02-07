package com.arvatosystems.t9t.out.be.tests

import org.joda.time.DateTimeZone
import org.junit.Test
import org.junit.Assert

class TimeZonesTest {
    @Test
    def void getZoneIds() {
        val ids = DateTimeZone.availableIDs
        Assert.assertTrue(ids.contains("Europe/Berlin"))

        println("Number of time zones found: " + ids.size)
        println(ids.join("\n"))
    }
}
