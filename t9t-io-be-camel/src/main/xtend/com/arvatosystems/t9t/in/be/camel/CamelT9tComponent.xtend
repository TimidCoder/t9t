package com.arvatosystems.t9t.in.be.camel

import de.jpaw.annotations.AddLogger
import java.util.Map
import org.apache.camel.Component
import org.apache.camel.impl.DefaultComponent

@AddLogger
class CamelT9tComponent extends DefaultComponent implements Component {

    override protected createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        val endpoint = new CamelT9tEndpoint(uri, this, remaining)
        setProperties(endpoint, parameters)
        return endpoint
    }
}
