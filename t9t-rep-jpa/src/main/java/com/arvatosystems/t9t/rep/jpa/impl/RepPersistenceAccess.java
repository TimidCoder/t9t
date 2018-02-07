package com.arvatosystems.t9t.rep.jpa.impl;

import com.arvatosystems.t9t.rep.ReportConfigDTO;
import com.arvatosystems.t9t.rep.ReportConfigRef;
import com.arvatosystems.t9t.rep.ReportParamsDTO;
import com.arvatosystems.t9t.rep.ReportParamsRef;
import com.arvatosystems.t9t.rep.be.request.restriction.IReportConfigResolverRestriction;
import com.arvatosystems.t9t.rep.jpa.mapping.IReportConfigDTOMapper;
import com.arvatosystems.t9t.rep.jpa.mapping.IReportParamsDTOMapper;
import com.arvatosystems.t9t.rep.jpa.persistence.IReportConfigEntityResolver;
import com.arvatosystems.t9t.rep.jpa.persistence.IReportParamsEntityResolver;
import com.arvatosystems.t9t.rep.services.IRepPersistenceAccess;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Singleton;

@Singleton
public class RepPersistenceAccess implements IRepPersistenceAccess {

    private final IReportParamsEntityResolver reportParamsResolver = Jdp.getRequired(IReportParamsEntityResolver.class);
    private final IReportParamsDTOMapper      reportParamsMapper   = Jdp.getRequired(IReportParamsDTOMapper.class);
    private final IReportConfigEntityResolver reportConfigResolver = Jdp.getRequired(IReportConfigEntityResolver.class);
    private final IReportConfigDTOMapper      reportConfigMapper   = Jdp.getRequired(IReportConfigDTOMapper.class);
    private final IReportConfigResolverRestriction reportConfigResolverRestriction = Jdp.getRequired(IReportConfigResolverRestriction.class);

    @Override
    public ReportConfigDTO getConfigDTO(ReportConfigRef configRef) throws Exception {
        return reportConfigMapper.mapToDto(
                reportConfigResolverRestriction.apply(reportConfigResolver.getEntityData(configRef, true)));
    }

    @Override
    public ReportParamsDTO getParamsDTO(ReportParamsRef paramsRef) throws Exception {
        return reportParamsMapper.mapToDto(reportParamsResolver.getEntityData(paramsRef, true));
    }

}
