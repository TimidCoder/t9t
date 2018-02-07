package com.arvatosystems.t9t.msglog.be.request;

import com.arvatosystems.t9t.base.T9tException;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.msglog.jpa.entities.MessageEntity;
import com.arvatosystems.t9t.msglog.jpa.persistence.IMessageEntityResolver;
import com.arvatosystems.t9t.msglog.request.RetrieveParametersRequest;
import com.arvatosystems.t9t.msglog.request.RetrieveParametersResponse;

import de.jpaw.dp.Jdp;

public class RetrieveParametersRequestHandler extends AbstractRequestHandler<RetrieveParametersRequest> {
    protected final IMessageEntityResolver resolver = Jdp.getRequired(IMessageEntityResolver.class);

    @Override
    public RetrieveParametersResponse execute(RequestContext ctx, RetrieveParametersRequest rq) {
        final MessageEntity loggedRequest = resolver.find(rq.getProcessRef());
        if (loggedRequest == null)
            throw new T9tException(T9tException.RECORD_DOES_NOT_EXIST, rq.getProcessRef());

        RetrieveParametersResponse resp = new RetrieveParametersResponse();
        if (rq.getRequestParameters())
            resp.setRequestParameters(loggedRequest.getRequestParameters());
        if (rq.getServiceResponse())
            resp.setServiceResponse(loggedRequest.getResponse());
        return resp;
    }
}
