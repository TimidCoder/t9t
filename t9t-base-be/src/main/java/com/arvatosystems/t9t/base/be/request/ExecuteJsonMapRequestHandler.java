package com.arvatosystems.t9t.base.be.request;

import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.api.ServiceRequest;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.request.ExecuteJsonMapRequest;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.IExecutor;
import com.arvatosystems.t9t.base.services.RequestContext;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.MapParser;
import de.jpaw.dp.Jdp;

public class ExecuteJsonMapRequestHandler extends AbstractRequestHandler<ExecuteJsonMapRequest> {

    // @Inject
    protected final IExecutor messaging = Jdp.getRequired(IExecutor.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, ExecuteJsonMapRequest request) {
        BonaPortable obj = MapParser.asBonaPortable(request.getRequest(), ServiceRequest.meta$$requestParameters);
        RequestParameters rp = (RequestParameters)obj;
        return messaging.executeSynchronous(ctx, rp);
    }
}
