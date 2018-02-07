package com.arvatosystems.t9t.rep.be.request.restriction;

import java.util.List;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.jpa.IResolverRestriction;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.rep.ReportConfigRef;
import com.arvatosystems.t9t.rep.jpa.entities.ReportConfigEntity;


/**
 * ReportConfigResolverRestriction handles backend checking for reportConfigId permission.
 * @author RREN001
 *
 */
public interface IReportConfigResolverRestriction extends IResolverRestriction<ReportConfigRef, FullTrackingWithVersion, ReportConfigEntity> {

    /** get all of the permissionId that's allowed for the user */
    List<String> getPermissionIdList(RequestContext ctx);
}
