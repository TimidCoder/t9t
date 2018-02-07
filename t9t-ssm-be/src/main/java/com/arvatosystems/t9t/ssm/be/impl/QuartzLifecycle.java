package com.arvatosystems.t9t.ssm.be.impl;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.cfg.be.ConfigProvider;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Startup;
import de.jpaw.dp.StartupShutdown;

/**
 * This class is responsible for the life cycle of the scheduler.
 * It launches Quartz as the almost last service of the application.
 * As part of the shutdown sequence, it shuts down the scheduler service.
 */
@Startup(2000000012)
public class QuartzLifecycle implements StartupShutdown {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzLifecycle.class);

    private Scheduler scheduler = null;

    @Override
    public void onStartup() {
        final Boolean disableScheduler = ConfigProvider.getConfiguration().getDisableScheduler();
        if (Boolean.TRUE.equals(disableScheduler)) {
            LOGGER.info("Configuration sets scheduler to disabled - not starting quartz");
            return;
        } else {
            LOGGER.info("Starting quartz");
        }

        StdSchedulerFactory quartzSchedulerFactory = new StdSchedulerFactory();

        try {
            final IQuartzPropertyProvider propertyProvider = Jdp.getOptional(IQuartzPropertyProvider.class);
            if (propertyProvider != null) {
                quartzSchedulerFactory.initialize(propertyProvider.getProperties());
            }

            scheduler = quartzSchedulerFactory.getScheduler();
            Jdp.bindInstanceTo(scheduler, Scheduler.class);
//            scheduler.setJobFactory(myJdpJobFactory);             // not required with Jdp?
            scheduler.start();
        } catch (SchedulerException e) {
            LOGGER.error("Error creating Quartz scheduler.", e);
        }
    }

    @Override
    public void onShutdown() {
        if (scheduler == null)
            return;  // scheduler was not started - don't try to shut it down
        try {
            scheduler.shutdown(true);
        } catch (SchedulerException e) {
            LOGGER.error("Error shutting down Quartz scheduler", e);
        }
    }
}
