package com.arvatosystems.t9t.base.be.request;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.request.ErrorRequest;
import com.arvatosystems.t9t.base.services.AbstractReadOnlyRequestHandler;

/**
 * A technical Request handler which is used to pass exceptions thrown in outer transport layers through appropriate database logging and response message
 * translation.
 */
public class ErrorRequestHandler extends AbstractReadOnlyRequestHandler<ErrorRequest> {

    @Override
    public ServiceResponse execute(ErrorRequest errorRequest) {
        return error(errorRequest.getReturnCode(), errorRequest.getErrorDetails());
    }

}
