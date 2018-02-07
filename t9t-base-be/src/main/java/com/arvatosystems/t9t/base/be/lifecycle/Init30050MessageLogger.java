package com.arvatosystems.t9t.base.be.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.server.services.IRequestLogger;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Startup;
import de.jpaw.dp.StartupShutdown;


// start and stop the logger service at the desired point in time

@Startup(30050)
public class Init30050MessageLogger implements StartupShutdown {
    private static final Logger LOGGER = LoggerFactory.getLogger(Init30050MessageLogger.class);
    private IRequestLogger messageLogger;

    @Override
    public void onStartup() {
        LOGGER.info("Starting message logger...");
        messageLogger = Jdp.getRequired(IRequestLogger.class);
        messageLogger.open();
    }

    @Override
    public void onShutdown() {
        LOGGER.info("Shutting down message logger...");
        messageLogger.close();
    }
}
