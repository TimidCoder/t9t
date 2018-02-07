package com.arvatosystems.t9t.cfg.be;

import java.util.concurrent.atomic.AtomicBoolean;

public class StatusProvider {
    private static final AtomicBoolean shutdownInProgress = new AtomicBoolean(false);

    public static void setShutdownInProgress() {
        shutdownInProgress.set(true);
    }

    public static boolean isShutdownInProgress() {
        return shutdownInProgress.get();
    }
}
