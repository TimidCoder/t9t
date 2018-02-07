package com.arvatosystems.t9t.out.be.impl.output.camel;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;

/**
 * Interface for defining an endpoint specific transformer for camel messages.
 */
public interface ICamelOutMessageTransformer {

    /**
     * Method for processing the transformation.
     *
     * @param exchange
     *            incoming exchange
     * @param endpoint
     *            endpoint the message should be send to
     * @return the modified exchange
     */
    public Exchange transformMessage(Exchange exchange, Endpoint endpoint);

    /**
     * Type of endpoint this transformer is used for
     *
     * @return endpoint type
     */
    public Class<? extends Endpoint> forType();

}
