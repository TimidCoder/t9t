package com.arvatosystems.t9t.io.be.request;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.services.AbstractSearchRequestHandler;
import com.arvatosystems.t9t.io.jpa.mapping.ISinkDTOMapper;
import com.arvatosystems.t9t.io.jpa.persistence.ISinkEntityResolver;
import com.arvatosystems.t9t.io.request.SinkSearchRequest;

import de.jpaw.dp.Jdp;

public class SinkSearchRequestHandler extends AbstractSearchRequestHandler<SinkSearchRequest> {

//  @Inject
    private final ISinkEntityResolver sinksResolver = Jdp.getRequired(ISinkEntityResolver.class);
//  @Inject
    private final ISinkDTOMapper sinksMapper = Jdp.getRequired(ISinkDTOMapper.class);

    @Override
    public ServiceResponse execute(SinkSearchRequest request) throws Exception {
        sinksMapper.processSearchPrefixForDB(request);
        return sinksMapper.createReadAllResponse(sinksResolver.search(request, null), request.getSearchOutputTarget());
    }
}
