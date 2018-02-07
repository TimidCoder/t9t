package com.arvatosystems.t9t.io.be.camel.init;

import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.services.IFileUtil;
import com.arvatosystems.t9t.cfg.be.ConfigProvider;
import com.arvatosystems.t9t.io.DataSinkDTO;
import com.arvatosystems.t9t.out.be.impl.output.camel.AbstractExtensionCamelRouteBuilder;
import com.arvatosystems.t9t.out.be.impl.output.camel.GenericT9tRoute;
import com.arvatosystems.t9t.out.services.IOutPersistenceAccess;

import de.jpaw.bonaparte.pojos.api.media.MediaType;
import de.jpaw.dp.Jdp;
import de.jpaw.dp.Provider;
import de.jpaw.dp.Singleton;
import de.jpaw.dp.Startup;
import de.jpaw.dp.StartupShutdown;

/**
 *
 * @author LUEC034
 */
@Startup(95000)
@Singleton
public class CamelContextProvider implements StartupShutdown, Provider<CamelContext> {

    private CamelContext camelContext = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(CamelContextProvider.class);
    public static final String DEFAULT_ENVIRONMENT = "t9t";

    protected final IOutPersistenceAccess iOutPersistenceAccess = Jdp.getRequired(IOutPersistenceAccess.class);
    protected final IFileUtil fileUtil = Jdp.getRequired(IFileUtil.class);

    @Override
    public void onStartup() {
        camelContext = new DefaultCamelContext();
        Jdp.registerWithCustomProvider(CamelContext.class, this);
        // first Initialize routes that derive from
        // AbstractExtensionCamelRouteBuilder
        // these should be static routes that are not configurable (like
        // FileRoute)
        List<AbstractExtensionCamelRouteBuilder> classList = Jdp.getAll(AbstractExtensionCamelRouteBuilder.class);
        if (classList != null) {
            for (AbstractExtensionCamelRouteBuilder clazz : classList) {
                try {
                    LOGGER.info("Adding route: {}", clazz.getClass());
                    camelContext.addRoutes(clazz);
                } catch (Exception e) {
                    LOGGER.debug("There was a problem initializing route: {} with the following exception ", clazz.getClass(), e);
                }
            }
        } else {
            LOGGER.info("No AbstractExtensionCamelRouteBuilders found.");
        }
        // After initializing these static routes there are additional routes
        try {
            String environment = ConfigProvider.getConfiguration().getImportEnvironment();
            if (environment == null)
                environment = DEFAULT_ENVIRONMENT;
            List<DataSinkDTO> dataSinkDTOList = iOutPersistenceAccess.getDataSinkDTOsForEnvironment(environment);
            LOGGER.info("Looking for Camel import routes for environment {}: {} routes found", environment, dataSinkDTOList.size());
            for (DataSinkDTO dataSinkDTO : dataSinkDTOList) {
                if (validateT9TCamelComponent(dataSinkDTO)) { // only add the route if the validateT9tCamelComponent returns true
                    camelContext.addRoutes(new GenericT9tRoute(dataSinkDTO, fileUtil));
                } else {
                    LOGGER.error("There have been problems while configuring route with dataSinkID: {} ", dataSinkDTO.getDataSinkId());
                }
            }
            camelContext.start();

        } catch (Exception e) {
            LOGGER.error("CamelContext could not be started... ", e);
        }
    }

    @Override
    public CamelContext get() {
        return camelContext;
    }

    /**
     * Does a short validation of important fields like camelRoute, InputFormatConverter.
     * @param dataSink
     * @return True if the DataSink contains a camelRoute, commFormatName, preTransformerName and baseClassPqon. Otherwise false!
     *
     */
    private boolean validateT9TCamelComponent(DataSinkDTO dataSink) {
        if (dataSink.getCamelRoute() == null || dataSink.getCamelRoute().isEmpty()) {
            LOGGER.error("dataSink {} does not contain a camelRoute field.", dataSink.getDataSinkId());
            return false;
        }
        if (dataSink.getCommFormatType().getBaseEnum() == MediaType.USER_DEFINED && (dataSink.getCommFormatName() == null || dataSink.getCommFormatName().isEmpty())) {
            LOGGER.error("dataSink {} does not contain a commFormatName for an InputFormatConverter.", dataSink.getDataSinkId());
            return false;
        }
        if (dataSink.getPreTransformerName() == null || dataSink.getPreTransformerName().isEmpty()) {
            LOGGER.error("dataSink {} does not contain a preTransformerName for an InputDataTransformer.", dataSink.getDataSinkId());
            return false;
        }
        if (dataSink.getBaseClassPqon() == null || dataSink.getBaseClassPqon().isEmpty()) {
            LOGGER.error("dataSink {} does not contain a baseClassPqon.", dataSink.getDataSinkId());
            return false;
        }
        return true;
    }

    @Override
    public void onShutdown() {
        if (camelContext != null) {
            LOGGER.info("Shutting down Camel context");
            try {
                camelContext.stop();
                LOGGER.info("Camel context successfully shut down");
            } catch (Exception e) {
                LOGGER.error("Exception while shutting down Camel context:", e);
                // unfortunately we cannot do anything about it, but the system is shutting down anyway at this point
            }
        }
    }
}
