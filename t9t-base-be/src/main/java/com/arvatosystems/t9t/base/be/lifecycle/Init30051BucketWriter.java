package com.arvatosystems.t9t.base.be.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.services.IBucketWriter;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Startup;
import de.jpaw.dp.StartupShutdown;


// start and stop the bucket writer service at the desired point in time

@Startup(30051)
public class Init30051BucketWriter implements StartupShutdown {
    private static final Logger LOGGER = LoggerFactory.getLogger(Init30051BucketWriter.class);
    private IBucketWriter bucketWriter;

    @Override
    public void onStartup() {
        LOGGER.info("Starting bucket writer...");
        bucketWriter = Jdp.getRequired(IBucketWriter.class);
        bucketWriter.open();
    }

    @Override
    public void onShutdown() {
        LOGGER.info("Shutting down bucket writer...");
        bucketWriter.close();
    }
}
