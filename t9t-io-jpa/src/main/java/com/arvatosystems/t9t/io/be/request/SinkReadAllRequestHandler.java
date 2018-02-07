package com.arvatosystems.t9t.io.be.request;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.entities.FullTracking;
import com.arvatosystems.t9t.base.search.ReadAllResponse;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.io.SinkDTO;
import com.arvatosystems.t9t.io.jpa.mapping.ISinkDTOMapper;
import com.arvatosystems.t9t.io.jpa.persistence.ISinkEntityResolver;
import com.arvatosystems.t9t.io.request.SinkReadAllRequest;

import de.jpaw.dp.Jdp;

public class SinkReadAllRequestHandler extends AbstractRequestHandler<SinkReadAllRequest> {

//  @Inject
    private final ISinkEntityResolver sinksResolver = Jdp.getRequired(ISinkEntityResolver.class);
//  @Inject
    private final ISinkDTOMapper sinksMapper = Jdp.getRequired(ISinkDTOMapper.class);

    @Override
    public ServiceResponse execute(SinkReadAllRequest request) throws Exception {
        ReadAllResponse<SinkDTO, FullTracking> rs = new ReadAllResponse<SinkDTO, FullTracking>();
        rs.setDataList(sinksMapper.mapListToDwt(sinksResolver.readAll(request.getReturnOnlyActive())));
        rs.setReturnCode(0);
        return rs;
    }

}
