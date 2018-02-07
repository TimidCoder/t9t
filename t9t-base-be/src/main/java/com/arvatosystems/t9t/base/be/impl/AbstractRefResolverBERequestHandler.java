package com.arvatosystems.t9t.base.be.impl;

import com.arvatosystems.t9t.base.crud.RefResolverRequest;
import com.arvatosystems.t9t.base.crud.RefResolverResponse;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;

import de.jpaw.bonaparte.pojos.apiw.Ref;
import de.jpaw.bonaparte.refsw.RefResolver;

public abstract class AbstractRefResolverBERequestHandler<REF extends Ref, REQUEST extends RefResolverRequest<REF>> extends
        AbstractRequestHandler<REQUEST> {

    @Override
    public boolean isReadOnly(REQUEST params) {
        return true;
    }

    protected RefResolverResponse execute(REQUEST request, RefResolver<REF, ?, ?> resolver) {
        RefResolverResponse rs = new RefResolverResponse();
        rs.setKey(resolver.getRef(request.getRef()));
        rs.setReturnCode(0);
        return rs;
    }
}
