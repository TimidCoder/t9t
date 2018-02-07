package com.arvatosystems.t9t.jdp;

import java.util.function.Consumer;

import org.reflections.Reflections;

import com.arvatosystems.t9t.init.InitContainers;

import de.jpaw.dp.Jdp;

public class Init {

    /** Initializes Jdp using defaults. */
    public static void initializeT9t() {
        initializeT9t(null);
    }

    /** Initializes Jdp with the possibility to alter the automatically assigned classes before initialization. */
    public static void initializeT9t(Consumer<Reflections []> callback) {
        Jdp.reset();
        // Jdp.excludePackagePrefix("java.");
        Jdp.includePackagePrefix("de.jpaw.");
        Jdp.includePackagePrefix("com.arvatosystems.");

        Reflections [] scannedPackages = InitContainers.initializeT9t();
        Jdp.scanClasses(scannedPackages);
        if (callback != null)
            callback.accept(scannedPackages);
        Jdp.runStartups(scannedPackages);
    }
}
