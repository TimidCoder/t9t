package com.arvatosystems.t9t.demo.be.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jpaw.dp.Singleton;

@Singleton
public class DemoSingleton implements IDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoSingleton.class);

    static {
        LOGGER.info("Demo singleton class initialized");
    }

    public DemoSingleton() {
        LOGGER.info("Demo singleton class instantiated");
    }

    @Override
    public void doNothing() {
    }

}
