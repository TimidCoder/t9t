package com.arvatosystems.t9t.base.be.lifecycle;


import com.arvatosystems.t9t.base.be.execution.RequestContextScope;
import com.arvatosystems.t9t.base.services.RequestContext;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Startup;

// this is required before the JPA layer initializes, because it injects the contextprovider
@Startup(1800)
public class Init01800Basics {
    public static void onStartup() {

        // create a custom scope for the request context. This is for the getters!
//        Jdp.registerWithCustomProvider(RequestContext.class, RequestContextWithScope.getProvider());
        Jdp.registerWithCustomProvider(RequestContext.class, Jdp.getRequired(RequestContextScope.class));
    }
}
