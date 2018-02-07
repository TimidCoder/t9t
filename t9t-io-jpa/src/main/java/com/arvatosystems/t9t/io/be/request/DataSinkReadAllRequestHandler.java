package com.arvatosystems.t9t.io.be.request;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.search.ReadAllResponse;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.io.DataSinkDTO;
import com.arvatosystems.t9t.io.jpa.mapping.IDataSinkDTOMapper;
import com.arvatosystems.t9t.io.jpa.persistence.IDataSinkEntityResolver;
import com.arvatosystems.t9t.io.request.DataSinkReadAllRequest;

import de.jpaw.dp.Jdp;

public class DataSinkReadAllRequestHandler extends AbstractRequestHandler<DataSinkReadAllRequest> {

//  @Inject
    private final IDataSinkEntityResolver sinksResolver = Jdp.getRequired(IDataSinkEntityResolver.class);

//  @Inject
    private final IDataSinkDTOMapper sinksMapper = Jdp.getRequired(IDataSinkDTOMapper.class);

    @Override
    public ServiceResponse execute(DataSinkReadAllRequest request) throws Exception {
        ReadAllResponse<DataSinkDTO, FullTrackingWithVersion> rs = new ReadAllResponse<DataSinkDTO, FullTrackingWithVersion>();
        rs.setDataList(sinksMapper.mapListToDwt(sinksResolver.readAll(request.getReturnOnlyActive())));
        rs.setReturnCode(0);
        return rs;
    }
}
