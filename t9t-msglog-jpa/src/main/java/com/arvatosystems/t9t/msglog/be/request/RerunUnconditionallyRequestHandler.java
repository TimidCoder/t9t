package com.arvatosystems.t9t.msglog.be.request;

import com.arvatosystems.t9t.base.T9tException;
import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.IExecutor;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.msglog.jpa.entities.MessageEntity;
import com.arvatosystems.t9t.msglog.jpa.persistence.IMessageEntityResolver;
import com.arvatosystems.t9t.msglog.request.RerunRequest;

import de.jpaw.dp.Jdp;

public class RerunUnconditionallyRequestHandler extends AbstractRequestHandler<RerunRequest> {
    protected final IExecutor executor = Jdp.getRequired(IExecutor.class);
    protected final IMessageEntityResolver resolver = Jdp.getRequired(IMessageEntityResolver.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, RerunRequest rq) {
        final MessageEntity loggedRequest = resolver.find(rq.getProcessRef());
        if (loggedRequest == null)
            throw new T9tException(T9tException.RECORD_DOES_NOT_EXIST, rq.getProcessRef());
//      if (loggedRequest.getRerunByProcessRef() != null)
//          throw new T9tException(T9tException.RERUN_NOT_APPLICABLE_DONE, rq.getProcessRef());
//      if (ApplicationException.isOk(loggedRequest.getReturnCode()))
//          throw new T9tException(T9tException.RERUN_NOT_APPLICABLE_RET, rq.getProcessRef());
        // all checks OK: perform the rerun

        RequestParameters recordedRequest = loggedRequest.getRequestParameters();
        if (recordedRequest == null)
            throw new T9tException(T9tException.RERUN_NOT_POSSIBLE_NO_RECORDED_REQUEST, rq.getProcessRef());

        return executor.executeSynchronous(ctx, recordedRequest);
    }
}
