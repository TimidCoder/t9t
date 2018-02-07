package com.arvatosystems.t9t.base.be.request;

import com.arvatosystems.t9t.base.T9tException;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.request.ExceptionRequest;
import com.arvatosystems.t9t.base.services.AbstractReadOnlyRequestHandler;

public class ExceptionRequestHandler extends AbstractReadOnlyRequestHandler<ExceptionRequest> {

    @Override
    public ServiceResponse execute(ExceptionRequest errorRequest) {
        final int code = errorRequest.getReturnCode();
        final String message = errorRequest.getErrorMessage();
        if (code > 0) {
            if (message == null)
                throw new T9tException(code);
            else
                throw new T9tException(code, message);
        } else {
            if (message == null)
                throw new RuntimeException();
            else
                throw new RuntimeException(message);
        }
    }
}
