package com.arvatosystems.t9t.client.init;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractJndiConfigurationProvider extends AbstractConfigurationProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(JndiConfigurationProvider.class);

    private static final String lookup(Context context, String name) throws NamingException {
        try {
            Object val = context.lookup(name);
            if (val != null) {
                if (val instanceof String) {
                    String vals = (String)val;
                    LOGGER.debug("Obtained {} via JNDI as {}", name, vals);
                    return vals;
                }
                LOGGER.error("Obtained {} via JNDI, but of type {}, want a String - using default value", name, val.getClass().getCanonicalName());
                return null;
            }
        } catch (NameNotFoundException e) {
            // not severe - warn and use the default value
            // fall trough
        }
        LOGGER.debug("Unable to find {} via JNDI - using default value", name);
        return null;
    }

    protected AbstractJndiConfigurationProvider(Context ctx) throws NamingException {
        super(
          "JNDI",
          lookup(ctx, "t9t/port"),
          lookup(ctx, "t9t/host"),
          lookup(ctx, "t9t/rpcpath"),
          lookup(ctx, "t9t/authpath")
        );
    }
}
