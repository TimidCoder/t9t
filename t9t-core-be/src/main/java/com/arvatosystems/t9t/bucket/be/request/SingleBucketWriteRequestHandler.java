package com.arvatosystems.t9t.bucket.be.request;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.event.BucketWriteKey;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.IBucketWriter;
import com.arvatosystems.t9t.base.services.IExecutor;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.bucket.request.SingleBucketWriteRequest;

import de.jpaw.dp.Jdp;

public class SingleBucketWriteRequestHandler extends AbstractRequestHandler<SingleBucketWriteRequest> {
    protected final IBucketWriter bucketWriter = Jdp.getRequired(IBucketWriter.class);
    protected final IExecutor executor = Jdp.getRequired(IExecutor.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, SingleBucketWriteRequest rq) {
        if (!rq.getAsync()) {
            Map<BucketWriteKey, Integer> cmds = new HashMap<BucketWriteKey, Integer>(2 * rq.getValues().size());
            for (Map.Entry<String, Integer> me: rq.getValues().entrySet())
                cmds.put(new BucketWriteKey(ctx.tenantRef, rq.getObjectRef(), me.getKey()), me.getValue());
            bucketWriter.writeToBuckets(cmds);
        } else {
            for (Map.Entry<String, Integer> me: rq.getValues().entrySet())
                executor.writeToBuckets(Collections.singleton(me.getKey()), rq.getObjectRef(), me.getValue());
        }
        return ok();
    }
}
