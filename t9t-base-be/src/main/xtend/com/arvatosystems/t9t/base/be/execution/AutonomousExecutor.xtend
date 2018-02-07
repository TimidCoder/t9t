package com.arvatosystems.t9t.base.be.execution

import com.arvatosystems.t9t.base.services.IAutonomousExecutor
import com.arvatosystems.t9t.cfg.be.ConfigProvider
import com.arvatosystems.t9t.server.services.IRequestProcessor
import de.jpaw.annotations.AddLogger
import de.jpaw.dp.Inject
import de.jpaw.dp.Singleton
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.base.api.RequestParameters

@AddLogger
@Singleton
class AutonomousExecutor implements IAutonomousExecutor {
    private static final int MAX_AUTONOMOUS_TRANSACTIONS = 4 // should this be configurable or dynamic? Or share the pool with the Async processor?

    protected final ExecutorService executorService
    @Inject IRequestProcessor requestProcessor

    new() {
        val autoPoolSizeByCfg = ConfigProvider.configuration.applicationConfiguration?.autonomousPoolSize
        val autoPoolSize = autoPoolSizeByCfg ?: MAX_AUTONOMOUS_TRANSACTIONS
        LOGGER.info("Creating a new thread pool for autonomous transactions of size {}", autoPoolSize);

        val counter = new AtomicInteger()
        executorService =  Executors.newFixedThreadPool(autoPoolSize) [
            val threadName = "t9t-autonomous-" + counter.incrementAndGet
            LOGGER.info("Launching thread {} of {} for local autonomous transactions", threadName, autoPoolSize)
            return new Thread(it, threadName)
        ]
    }

    override execute(RequestContext ctx, RequestParameters rp) {
        val f = executorService.submit [
            requestProcessor.execute(rp, ctx.internalHeaderParameters.jwtInfo, ctx.internalHeaderParameters.encodedJwt, true)
        ]
        return f.get
    }
}
