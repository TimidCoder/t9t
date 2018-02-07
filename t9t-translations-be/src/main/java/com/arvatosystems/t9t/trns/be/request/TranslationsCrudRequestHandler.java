package com.arvatosystems.t9t.trns.be.request;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.be.impl.AbstractCrudSurrogateKeyBERequestHandler;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.trns.TranslationsDTO;
import com.arvatosystems.t9t.trns.TranslationsRef;
import com.arvatosystems.t9t.trns.request.TranslationsCrudRequest;
import com.arvatosystems.t9t.trns.services.ITranslationsResolver;

import de.jpaw.dp.Jdp;

public class TranslationsCrudRequestHandler extends AbstractCrudSurrogateKeyBERequestHandler<TranslationsRef, TranslationsDTO, FullTrackingWithVersion, TranslationsCrudRequest> {

    protected final ITranslationsResolver resolver = Jdp.getRequired(ITranslationsResolver.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, TranslationsCrudRequest crudRequest) throws Exception {
        return execute(ctx, crudRequest, resolver);
    }
}
