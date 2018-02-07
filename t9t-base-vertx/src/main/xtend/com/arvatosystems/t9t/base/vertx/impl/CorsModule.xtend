package com.arvatosystems.t9t.base.vertx.impl

import com.arvatosystems.t9t.base.api.ServiceResponse
import com.arvatosystems.t9t.base.vertx.IServiceModule
import de.jpaw.annotations.AddLogger
import de.jpaw.bonaparte.api.codecs.IMessageCoderFactory
import de.jpaw.bonaparte.core.BonaPortable
import de.jpaw.dp.Dependent
import de.jpaw.dp.Named
import io.vertx.core.Vertx
import io.vertx.ext.web.Router

@AddLogger
@Named("cors")
@Dependent
class CorsModule implements IServiceModule {
    override getExceptionOffset() {
        return -1 // must be before the auth handler
    }

    override getModuleName() {
        return "cors"
    }

    override void mountRouters(Router router, Vertx vertx, IMessageCoderFactory<BonaPortable, ServiceResponse, byte[]> coderFactory) {
        LOGGER.info("Registering module {}", moduleName)
        HttpUtils.addCorsOptionsRouter(router, "rpc")
    }
}
