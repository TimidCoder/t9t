package com.arvatosystems.t9t.in.be.camel

import de.jpaw.annotations.AddLogger
import org.apache.camel.Consumer
import org.apache.camel.Processor
import org.apache.camel.Producer
import org.apache.camel.impl.DefaultEndpoint
/**
 * CamelT9tEndpoint for the 't9t:*' camel component
 * To be noted: If any additional parameters should be added to the camel route (as in t9t:ROUTE?apiKey=* )
 * they have to be added here as a class variable with getters & setters.
 *
 */
@AddLogger
class CamelT9tEndpoint extends DefaultEndpoint {

    /*
     * Defines the APIKey
     */
    private String apiKey =""

    new() {
    }

    new(String uri, CamelT9tComponent component, String url) {
        super(uri, component);
        LOGGER.info("Created a t9t camel endpoint for URI {}, remaining = {}", uri, url)
    }

    @Deprecated
    new(String endpointUri) {
        super(endpointUri);
    }

    override Producer createProducer() throws Exception {
        var camelProducer = new CamelT9tProducer(this)
        return camelProducer
    }

    override Consumer createConsumer(Processor processor) throws Exception {
        var consumer = new CamelT9tConsumer(this, processor)
        configureConsumer(consumer)
        return consumer;
    }

    override boolean isSingleton() {
        return true;
    }

    def String getApiKey() {
        return this.apiKey
    }
    def void setApiKey(String apiKey) {
        this.apiKey = apiKey
    }
}
