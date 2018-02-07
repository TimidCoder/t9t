package com.arvatosystems.t9t.doc.be.request;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.be.impl.AbstractCrudSurrogateKeyBERequestHandler;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.doc.DocTemplateDTO;
import com.arvatosystems.t9t.doc.DocTemplateRef;
import com.arvatosystems.t9t.doc.request.DocTemplateCrudRequest;
import com.arvatosystems.t9t.doc.services.IDocTemplateResolver;

import de.jpaw.dp.Jdp;

public class DocTemplateCrudRequestHandler extends AbstractCrudSurrogateKeyBERequestHandler<DocTemplateRef, DocTemplateDTO, FullTrackingWithVersion, DocTemplateCrudRequest> {

    protected final IDocTemplateResolver resolver = Jdp.getRequired(IDocTemplateResolver.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, DocTemplateCrudRequest crudRequest) throws Exception {
        return execute(ctx, crudRequest, resolver);
    }
}
