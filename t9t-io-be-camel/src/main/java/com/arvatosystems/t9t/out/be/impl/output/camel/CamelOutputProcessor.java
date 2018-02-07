package com.arvatosystems.t9t.out.be.impl.output.camel;

import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.jpaw.dp.Dependent;
import de.jpaw.dp.Jdp;
import de.jpaw.dp.Named;
import de.jpaw.dp.Provider;

/**
 * Class for processing all camel exports. The incoming message is dynamically routed to the endpoint specified for the name found in the header property
 * 'camelRoute'. The routes have to be configured in a properties file named 'camelEndpoints.properties'. According to the endpoint type the message is
 * eventually transformed via an implementation of {@linkplain ICamelOutMessageTransformer}, to match the expected format.
 */
@Dependent
@Named("outputCamelProcessor")
public class CamelOutputProcessor extends AbstractCamelProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CamelOutputProcessor.class);


    private final Provider<CamelContext> camelContext = Jdp.getProvider(CamelContext.class);

//    @Inject
    private final List<ICamelOutMessageTransformer> transformers = Jdp.getAll(ICamelOutMessageTransformer.class);

    public void process(Exchange exchange) throws Exception {
        String camelRoute = exchange.getIn().getHeader("camelRoute", String.class);
        // Get the endpoint uri
        String camelEndpoint = getEndporintURI(camelRoute);
        // Get the endpoint for the route
        Endpoint endpoint = camelContext.get().getEndpoint(camelEndpoint);

        // Transform the message
        ICamelOutMessageTransformer transformer = getTransformer(endpoint.getClass());

        if (transformer != null) {
            exchange = transformer.transformMessage(exchange, endpoint);
        }

        // Process the message via the endpoints producer

        Producer producer = endpoint.createProducer();

        producer.start();

        producer.process(exchange);

        producer.stop();

    }

    private ICamelOutMessageTransformer getTransformer(Class<? extends Endpoint> endpointClass) {
        if (transformers != null) {
            for (ICamelOutMessageTransformer transformer : transformers) {
                if (transformer.forType().equals(endpointClass)) {
                    return transformer;
                }
            }
        }
        return null;
    }
}
