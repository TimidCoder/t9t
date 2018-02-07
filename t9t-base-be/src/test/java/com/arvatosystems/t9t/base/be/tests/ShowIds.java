package com.arvatosystems.t9t.base.be.tests;

import java.nio.charset.Charset;

import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;

public class ShowIds {

    @Test
    public void showZoneIds() throws Exception {
        for (String s: DateTimeZone.getAvailableIDs()) {
            System.out.println(s);
        }
        Assert.assertTrue(DateTimeZone.getAvailableIDs().contains("Europe/Berlin"));
    }
    @Test
    public void showEncodings() throws Exception {
        for (String s: Charset.availableCharsets().keySet()) {
            System.out.println(s);
        }
        Assert.assertTrue(Charset.availableCharsets().get("ISO-8859-1") != null);
    }
}
