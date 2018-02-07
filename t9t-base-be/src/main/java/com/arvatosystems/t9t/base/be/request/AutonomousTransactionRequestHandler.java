package com.arvatosystems.t9t.base.be.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.request.AutonomousTransactionRequest;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.IAutonomousExecutor;
import com.arvatosystems.t9t.base.services.RequestContext;

import de.jpaw.annotations.AddLogger;
import de.jpaw.dp.Jdp;

@AddLogger
public class AutonomousTransactionRequestHandler extends AbstractRequestHandler<AutonomousTransactionRequest> {
    private final static Logger LOGGER = LoggerFactory.getLogger(com.arvatosystems.t9t.base.be.request.AutonomousTransactionRequestHandler.class);
    protected final IAutonomousExecutor autoExecutor = Jdp.getRequired(IAutonomousExecutor.class);

    @Override
    public ServiceResponse execute(final RequestContext ctx, final AutonomousTransactionRequest rq) {
        AutonomousTransactionRequestHandler.LOGGER.debug("Launching request {} in a different thread", rq.getRequest().ret$PQON());
        return autoExecutor.execute(ctx, rq.getRequest());
    }
}
