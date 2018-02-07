package com.arvatosystems.t9t.base.be.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.T9tException;
import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.auth.PermissionType;
import com.arvatosystems.t9t.base.be.impl.SimpleCallOutExecutor;
import com.arvatosystems.t9t.base.request.ExecuteRemoteRequest;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.IExecutor;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.server.services.IAuthorize;

import de.jpaw.bonaparte.pojos.api.OperationType;
import de.jpaw.bonaparte.pojos.api.auth.Permissionset;
import de.jpaw.bonaparte.pojos.api.media.MediaType;
import de.jpaw.dp.Jdp;

public class ExecuteRemoteRequestHandler extends AbstractRequestHandler<ExecuteRemoteRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteRemoteRequestHandler.class);

    protected final IExecutor executor = Jdp.getRequired(IExecutor.class);
    protected final IAuthorize authorizator = Jdp.getRequired(IAuthorize.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, ExecuteRemoteRequest request) throws Exception {
        RequestParameters remoteRequest = request.getRemoteRequest();
        Permissionset permissions = authorizator.getPermissions(ctx.internalHeaderParameters.getJwtInfo(), PermissionType.EXTERNAL, remoteRequest.ret$PQON());
        LOGGER.debug("External execution execution permissions checked for request {}, got {}", remoteRequest.ret$PQON(), permissions);
        boolean allowed = permissions.contains(OperationType.EXECUTE);
        if (!allowed) {
            throw new T9tException(T9tException.ACCESS_DENIED, "No EXECUTE permission on {}.{}", PermissionType.EXTERNAL, remoteRequest.ret$PQON());
        }
        // obtain an remoter for single use
        SimpleCallOutExecutor remoteClient = new SimpleCallOutExecutor(request.getUrl(), MediaType.COMPACT_BONAPARTE);
        return remoteClient.execute(ctx, request.getRemoteRequest());
    }
}
