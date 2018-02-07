package com.arvatosystems.t9t.io.be.request;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.services.AbstractSearchRequestHandler;
import com.arvatosystems.t9t.io.jpa.mapping.IDataSinkDTOMapper;
import com.arvatosystems.t9t.io.jpa.persistence.IDataSinkEntityResolver;
import com.arvatosystems.t9t.io.request.DataSinkSearchRequest;

import de.jpaw.dp.Jdp;

public class DataSinkSearchRequestHandler extends AbstractSearchRequestHandler<DataSinkSearchRequest> {

//  @Inject
    private final IDataSinkEntityResolver sinksResolver = Jdp.getRequired(IDataSinkEntityResolver.class);

//  @Inject
    private final IDataSinkDTOMapper sinksMapper = Jdp.getRequired(IDataSinkDTOMapper.class);

    @Override
    public ServiceResponse execute(DataSinkSearchRequest request) throws Exception {
        return sinksMapper.createReadAllResponse(sinksResolver.search(request, null), request.getSearchOutputTarget());
    }
}
