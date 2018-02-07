package com.arvatosystems.t9t.base.be.request;

import java.util.Map;

import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.api.ServiceRequest;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.request.ExecuteJsonRequest;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.IExecutor;
import com.arvatosystems.t9t.base.services.RequestContext;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.MapParser;
import de.jpaw.dp.Jdp;
import de.jpaw.json.JsonParser;

public class ExecuteJsonRequestHandler extends AbstractRequestHandler<ExecuteJsonRequest> {

    // @Inject
    protected final IExecutor messaging = Jdp.getRequired(IExecutor.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, ExecuteJsonRequest request) {
        Map<String, Object> map = new JsonParser(request.getRequest(), false).parseObject();
        BonaPortable obj = MapParser.asBonaPortable(map, ServiceRequest.meta$$requestParameters);
        RequestParameters rp = (RequestParameters)obj;
        return messaging.executeSynchronous(ctx, rp);
    }
}
