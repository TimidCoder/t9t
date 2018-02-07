package com.arvatosystems.t9t.rep.be.request;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.services.AbstractSearchRequestHandler;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.rep.be.request.restriction.IReportConfigByUserPermissionRestriction;
import com.arvatosystems.t9t.rep.jpa.mapping.IReportConfigDTOMapper;
import com.arvatosystems.t9t.rep.jpa.persistence.IReportConfigEntityResolver;
import com.arvatosystems.t9t.rep.request.ReportConfigSearchRequest;

import de.jpaw.dp.Jdp;

public class ReportConfigSearchRequestHandler extends AbstractSearchRequestHandler<ReportConfigSearchRequest> {
    protected final IReportConfigEntityResolver resolver = Jdp.getRequired(IReportConfigEntityResolver.class);
    protected final IReportConfigDTOMapper mapper = Jdp.getRequired(IReportConfigDTOMapper.class);
    protected final IReportConfigByUserPermissionRestriction reportConfigByUserPermissionRestriction = Jdp.getRequired(IReportConfigByUserPermissionRestriction.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, ReportConfigSearchRequest request) throws Exception {
        reportConfigByUserPermissionRestriction.apply(ctx, request);
        return mapper.createReadAllResponse(resolver.search(request, null), request.getSearchOutputTarget());
    }
}
