package com.arvatosystems.t9t.client.connection;

import java.util.List;

import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.request.BatchRequest;

// sole purpose is to keep project t9t-client-complex Java 6 compatible
public abstract class AbstractRemoteConnection implements IRemoteConnection {
    @Override
    public ServiceResponse execute(String authenticationHeader, List<RequestParameters> rpList) {
        if (rpList.isEmpty()) {
            // should be configurable, we might want to see them in the logs...
            return new ServiceResponse();
        }
        if (rpList.size() == 1) {
            return execute(authenticationHeader, rpList.get(0));
        } else {
            BatchRequest batchRequest = new BatchRequest();
            batchRequest.setAllowNo(false);
            batchRequest.setCommands(rpList);
            return execute(authenticationHeader, batchRequest);
        }
    }
}
