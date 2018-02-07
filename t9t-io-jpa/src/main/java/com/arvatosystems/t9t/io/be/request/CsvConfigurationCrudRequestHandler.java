package com.arvatosystems.t9t.io.be.request;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.jpa.impl.AbstractCrudSurrogateKey42RequestHandler;
import com.arvatosystems.t9t.io.CsvConfigurationDTO;
import com.arvatosystems.t9t.io.CsvConfigurationRef;
import com.arvatosystems.t9t.io.jpa.entities.CsvConfigurationEntity;
import com.arvatosystems.t9t.io.jpa.mapping.ICsvConfigurationDTOMapper;
import com.arvatosystems.t9t.io.jpa.persistence.ICsvConfigurationEntityResolver;
import com.arvatosystems.t9t.io.request.CsvConfigurationCrudRequest;

import de.jpaw.dp.Jdp;

public class CsvConfigurationCrudRequestHandler extends AbstractCrudSurrogateKey42RequestHandler<CsvConfigurationRef, CsvConfigurationDTO, FullTrackingWithVersion, CsvConfigurationCrudRequest, CsvConfigurationEntity> {

//  @Inject
    private final ICsvConfigurationEntityResolver sinksResolver = Jdp.getRequired(ICsvConfigurationEntityResolver.class);

//  @Inject
    private final ICsvConfigurationDTOMapper sinksMapper = Jdp.getRequired(ICsvConfigurationDTOMapper.class);

    @Override
    public ServiceResponse execute(CsvConfigurationCrudRequest crudRequest) throws Exception {
        return execute(sinksMapper, sinksResolver, crudRequest);
    }
}
