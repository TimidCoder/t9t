package com.arvatosystems.t9t.doc.be.request;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.be.impl.AbstractCrudSurrogateKeyBERequestHandler;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.services.IExecutor;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.doc.DocComponentDTO;
import com.arvatosystems.t9t.doc.DocComponentRef;
import com.arvatosystems.t9t.doc.be.impl.DocFormatter;
import com.arvatosystems.t9t.doc.request.DocComponentCrudRequest;
import com.arvatosystems.t9t.doc.services.IDocComponentResolver;

import de.jpaw.dp.Jdp;

public class DocComponentCrudRequestHandler extends AbstractCrudSurrogateKeyBERequestHandler<DocComponentRef, DocComponentDTO, FullTrackingWithVersion, DocComponentCrudRequest> {

    protected final IDocComponentResolver resolver = Jdp.getRequired(IDocComponentResolver.class);
    protected final IExecutor executor = Jdp.getRequired(IExecutor.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, DocComponentCrudRequest crudRequest) throws Exception {
        DocFormatter.clearCache();   // this could change the configuration, clear the cache immediately!
        executor.clearCache(DocComponentDTO.class.getSimpleName(), null);
        return execute(ctx, crudRequest, resolver);
    }
}
