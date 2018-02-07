package com.arvatosystems.t9t.demo.be.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jpaw.dp.Startup;

@Startup(9999)
public class DemoStartup {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemoStartup.class);

    public static void onStartup() {
        LOGGER.info("Startup code in extra JARs");
    }
}
