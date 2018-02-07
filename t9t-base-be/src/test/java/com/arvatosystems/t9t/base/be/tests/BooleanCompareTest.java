package com.arvatosystems.t9t.base.be.tests;

import org.junit.Assert;
import org.junit.Test;

public class BooleanCompareTest {
    @Test
    public void testCompareBoolean() throws Exception {
        Boolean true1 = new Boolean(true);
        Boolean true2 = Boolean.valueOf(true);

        Assert.assertEquals(true1, true2);
        Assert.assertFalse(true1 == true2);
    }
}
