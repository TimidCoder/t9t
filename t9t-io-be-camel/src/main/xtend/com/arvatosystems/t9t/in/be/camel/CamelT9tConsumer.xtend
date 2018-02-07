package com.arvatosystems.t9t.in.be.camel

import de.jpaw.annotations.AddLogger
import org.apache.camel.Endpoint
import org.apache.camel.Processor
import org.apache.camel.impl.DefaultConsumer

@AddLogger
class CamelT9tConsumer extends DefaultConsumer {

    new(Endpoint endpoint, Processor processor) {
        super(endpoint, processor)
    }
}
