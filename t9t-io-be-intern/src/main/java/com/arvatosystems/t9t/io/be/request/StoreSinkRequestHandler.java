package com.arvatosystems.t9t.io.be.request;

import com.arvatosystems.t9t.base.search.SinkCreatedResponse;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.io.request.StoreSinkRequest;
import com.arvatosystems.t9t.out.services.IOutPersistenceAccess;

import de.jpaw.dp.Jdp;

public class StoreSinkRequestHandler extends AbstractRequestHandler<StoreSinkRequest> {
    // @Inject
    protected final IOutPersistenceAccess dpl = Jdp.getRequired(IOutPersistenceAccess.class);

    @Override
    public SinkCreatedResponse execute(RequestContext ctx, StoreSinkRequest rq) {
        Long sinkRef = dpl.getNewSinkKey();
        rq.getDataSink().setObjectRef(sinkRef);
        dpl.storeNewSink(rq.getDataSink());
        SinkCreatedResponse sinkCreatedResponse = new SinkCreatedResponse();
        sinkCreatedResponse.setSinkRef(sinkRef);
        sinkCreatedResponse.setReturnCode(0);
        return sinkCreatedResponse;
    }
}
