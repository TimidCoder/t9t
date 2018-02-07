package com.arvatosystems.t9t.base.be.tests;

import org.junit.Test;

import com.arvatosystems.t9t.base.MessagingUtil;
import com.arvatosystems.t9t.base.be.impl.DefaultRequestHandlerResolver;
import com.arvatosystems.t9t.base.be.stubs.NoTenantCustomization;
import com.arvatosystems.t9t.base.request.PauseRequest;
import com.arvatosystems.t9t.base.services.IRequestHandlerResolver;

import de.jpaw.dp.Jdp;

public class NoTenantCustomizationTest {

    @Test
    public void testDefaultHandlerName() {

        // prep:
        Jdp.bindInstanceTo(new DefaultRequestHandlerResolver(), IRequestHandlerResolver.class);

        String actualName = new NoTenantCustomization().getRequestHandlerClassname(new PauseRequest());
        assert(actualName.equals(MessagingUtil.TWENTYEIGHT_PACKAGE_PREFIX + ".base.be.request.PauseRequestHandler"));
    }
}
