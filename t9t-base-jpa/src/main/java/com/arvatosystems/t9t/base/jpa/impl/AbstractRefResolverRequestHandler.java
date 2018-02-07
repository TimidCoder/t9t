package com.arvatosystems.t9t.base.jpa.impl;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.crud.RefResolverRequest;
import com.arvatosystems.t9t.base.crud.RefResolverResponse;
import com.arvatosystems.t9t.base.jpa.IResolverSurrogateKey;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;

import de.jpaw.bonaparte.jpa.BonaPersistableKey;
import de.jpaw.bonaparte.jpa.BonaPersistableTracking;
import de.jpaw.bonaparte.pojos.api.TrackingBase;
import de.jpaw.bonaparte.pojos.apiw.Ref;

public abstract class AbstractRefResolverRequestHandler<REF extends Ref, TRACKING extends TrackingBase, ENTITY extends BonaPersistableKey<Long> & BonaPersistableTracking<TRACKING>, REQUEST extends RefResolverRequest<REF>>
        extends AbstractRequestHandler<REQUEST> {

    protected abstract IResolverSurrogateKey<REF, TRACKING, ENTITY> getResolver();

    @Override
    public ServiceResponse execute(REQUEST request) throws Exception {
        RefResolverResponse rs = new RefResolverResponse();
        rs.setKey(getResolver().getRef(request.getRef(), true));
        rs.setReturnCode(0);
        return rs;
    }

}
