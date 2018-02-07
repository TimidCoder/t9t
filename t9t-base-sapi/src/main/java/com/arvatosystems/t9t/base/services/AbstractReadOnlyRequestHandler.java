package com.arvatosystems.t9t.base.services;

import com.arvatosystems.t9t.base.api.RequestParameters;

public abstract class AbstractReadOnlyRequestHandler<REQUEST extends RequestParameters> extends AbstractRequestHandler<REQUEST> {

    @Override
    public boolean isReadOnly(REQUEST params) {
        return true;
    }
}
