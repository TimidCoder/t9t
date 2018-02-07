//package com.arvatosystems.t9t.auth.be.request;
//
//import com.arvatosystems.t9t.auth.ApiKeyDTO;
//import com.arvatosystems.t9t.auth.ApiKeyRef;
//import com.arvatosystems.t9t.auth.request.ApiKeyCrudRequest;
//import com.arvatosystems.t9t.auth.services.IApiKeyResolver;
//import com.arvatosystems.t9t.base.api.ServiceResponse;
//import com.arvatosystems.t9t.base.be.impl.AbstractCrudSurrogateKeyBERequestHandler;
//import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
//import com.arvatosystems.t9t.base.services.RequestContext;
//
//import de.jpaw.dp.Jdp;
//
//public class ApiKeyCrudRequestHandler extends AbstractCrudSurrogateKeyBERequestHandler<ApiKeyRef, ApiKeyDTO, FullTrackingWithVersion, ApiKeyCrudRequest> {
//
//    protected final IApiKeyResolver resolver = Jdp.getRequired(IApiKeyResolver.class);
//
//    @Override
//    public ServiceResponse execute(RequestContext ctx, ApiKeyCrudRequest crudRequest) throws Exception {
//        return execute(ctx, crudRequest, resolver);
//    }
//}
