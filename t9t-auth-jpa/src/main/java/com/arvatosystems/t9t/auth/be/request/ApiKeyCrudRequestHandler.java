package com.arvatosystems.t9t.auth.be.request;

import com.arvatosystems.t9t.auth.ApiKeyDTO;
import com.arvatosystems.t9t.auth.ApiKeyRef;
import com.arvatosystems.t9t.auth.jpa.entities.ApiKeyEntity;
import com.arvatosystems.t9t.auth.jpa.mapping.IApiKeyDTOMapper;
import com.arvatosystems.t9t.auth.jpa.persistence.IApiKeyEntityResolver;
import com.arvatosystems.t9t.auth.request.ApiKeyCrudRequest;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.jpa.impl.AbstractCrudSurrogateKey42RequestHandler;
import com.arvatosystems.t9t.base.services.RequestContext;

import de.jpaw.dp.Jdp;

public class ApiKeyCrudRequestHandler extends AbstractCrudSurrogateKey42RequestHandler  <ApiKeyRef, ApiKeyDTO, FullTrackingWithVersion, ApiKeyCrudRequest, ApiKeyEntity> {

    private final IApiKeyDTOMapper mapper = Jdp.getRequired(IApiKeyDTOMapper.class);

    private final IApiKeyEntityResolver resolver = Jdp.getRequired(IApiKeyEntityResolver.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, ApiKeyCrudRequest crudRequest) {
        return execute(mapper, resolver, crudRequest);
    }
}
