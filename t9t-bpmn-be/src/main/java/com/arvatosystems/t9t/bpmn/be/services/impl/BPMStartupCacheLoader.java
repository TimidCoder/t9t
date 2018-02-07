package com.arvatosystems.t9t.bpmn.be.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.bpmn.services.IWorkflowStepCache;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Startup;
import de.jpaw.dp.StartupOnly;

@Startup(50080)
public class BPMStartupCacheLoader implements StartupOnly {
    private static final Logger LOGGER = LoggerFactory.getLogger(BPMStartupCacheLoader.class);

    @Override
    public void onStartup() {
        LOGGER.info("BPM Startup: caching object factory and workflow step implementations...");
        Jdp.getRequired(IWorkflowStepCache.class).loadCaches();
        LOGGER.info("BPM Startup complete");
    }
}
