package com.arvatosystems.t9t.client.init;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JndiConfigurationProvider extends AbstractJndiConfigurationProvider {
    public JndiConfigurationProvider() throws NamingException {
        super((Context)(new InitialContext()).lookup("java:comp/env"));
    }
}
