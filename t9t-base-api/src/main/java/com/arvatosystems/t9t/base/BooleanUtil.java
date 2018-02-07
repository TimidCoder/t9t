package com.arvatosystems.t9t.base;

public class BooleanUtil {
    private BooleanUtil() {} // don't want instances of this class

    /** Convenience method to avoid duplicate evaluation of b, to check if a Boolean is TRUE. */
    public static boolean isTrue(Boolean b) {
        return b == null ? false : b.booleanValue();
    }
}
