package com.arvatosystems.t9t.doc.be.request;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.be.impl.AbstractCrudSurrogateKeyBERequestHandler;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.doc.DocEmailCfgDTO;
import com.arvatosystems.t9t.doc.DocEmailCfgRef;
import com.arvatosystems.t9t.doc.request.DocEmailCfgCrudRequest;
import com.arvatosystems.t9t.doc.services.IDocEmailCfgResolver;

import de.jpaw.dp.Jdp;

public class DocEmailCfgCrudRequestHandler extends AbstractCrudSurrogateKeyBERequestHandler<DocEmailCfgRef, DocEmailCfgDTO, FullTrackingWithVersion, DocEmailCfgCrudRequest> {

    protected final IDocEmailCfgResolver resolver = Jdp.getRequired(IDocEmailCfgResolver.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, DocEmailCfgCrudRequest crudRequest) throws Exception {
        return execute(ctx, crudRequest, resolver);
    }
}
