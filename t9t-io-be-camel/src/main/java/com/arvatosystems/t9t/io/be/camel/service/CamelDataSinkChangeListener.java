package com.arvatosystems.t9t.io.be.camel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.event.EventParameters;
import com.arvatosystems.t9t.base.services.IEventHandler;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.io.DataSinkDTO;
import com.arvatosystems.t9t.io.event.DataSinkChangedEvent;
import com.arvatosystems.t9t.out.be.impl.output.camel.CamelOutputProcessor;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Named;
import de.jpaw.dp.Singleton;

@Singleton
@Named("IOCamelDataSinkChange")
public class CamelDataSinkChangeListener implements IEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CamelDataSinkChangeListener.class);

    private final CamelService camelService = Jdp.getRequired(CamelService.class);

    @SuppressWarnings("incomplete-switch")
    @Override
    public int execute(RequestContext context, EventParameters untypedEvent) {
        final DataSinkChangedEvent event = (DataSinkChangedEvent) untypedEvent;
        final DataSinkDTO dataSink = event.getDataSink();

        switch (event.getOperation()) {
        case INACTIVATE:
        case DELETE: {
            LOGGER.debug("Config of data sink id {} changed ({}) - remove routes", dataSink.getDataSinkId(), event.getOperation());
            remove(dataSink);
            break;
        }

        case ACTIVATE:
        case CREATE: {
            LOGGER.debug("Config of data sink id {} changed ({}) - add routes", dataSink.getDataSinkId(), event.getOperation());
            add(dataSink);
            break;
        }

        case MERGE:
        case PATCH:
        case UPDATE: {
            LOGGER.debug("Config of data sink id {} changed ({}) - update routes", dataSink.getDataSinkId(), event.getOperation());
            remove(dataSink);
            add(dataSink);
            break;
        }
        }

        return 0;
    }

    private void remove(DataSinkDTO dataSink) {
        camelService.removeRoutes(dataSink);
    }

    private void add(DataSinkDTO dataSink) {

        if (Boolean.FALSE.equals(dataSink.getIsInput())
            && dataSink.getCamelRoute() != null) {
            // If an camelRoute (which is loaded from camelEndpoint.properties) exist, also reload this config
            CamelOutputProcessor.flushConfig();
        }

        if (Boolean.TRUE.equals(dataSink.getIsInput())) {
            camelService.addRoutes(dataSink);
        }
    }

}
