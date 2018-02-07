package com.arvatosystems.t9t.base.services;

import com.arvatosystems.t9t.base.search.SearchCriteria;

import de.jpaw.bonaparte.pojos.api.OperationType;

public abstract class AbstractSearchRequestHandler<REQUEST extends SearchCriteria> extends AbstractRequestHandler<REQUEST> {

    @Override
    public boolean isReadOnly(REQUEST params) {
        return true;
    }

    @Override
    public OperationType getAdditionalRequiredPermission(REQUEST request) {
        return request.getSearchOutputTarget() != null ? OperationType.EXPORT : OperationType.SEARCH;       // must have permission EXPORT for output to data sink
    }
}
