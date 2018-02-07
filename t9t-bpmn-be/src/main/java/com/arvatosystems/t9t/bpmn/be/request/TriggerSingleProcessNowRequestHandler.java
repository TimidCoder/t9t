package com.arvatosystems.t9t.bpmn.be.request;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.IExecutor;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.bpmn.request.TriggerSingleProcessNowRequest;
import com.arvatosystems.t9t.bpmn.services.IBpmnRunner;

import de.jpaw.dp.Jdp;

public class TriggerSingleProcessNowRequestHandler extends AbstractRequestHandler<TriggerSingleProcessNowRequest> {
    protected final IBpmnRunner runner = Jdp.getRequired(IBpmnRunner.class);
    protected final IExecutor executor = Jdp.getRequired(IExecutor.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, TriggerSingleProcessNowRequest rq) {
        boolean again = runner.run(ctx, rq.getProcessStatusRef());
        if (again)
            executor.executeAsynchronous(ctx, rq);
        return ok();
    }
}
