package com.arvatosystems.t9t.base.be.impl;

import java.util.List;

import com.arvatosystems.t9t.base.output.OutputSessionParameters;
import com.arvatosystems.t9t.base.search.ReadAllResponse;
import com.arvatosystems.t9t.base.services.IExporterTool;
import com.arvatosystems.t9t.base.services.IOutputSession;
import com.google.common.collect.ImmutableList;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.pojos.api.TrackingBase;
import de.jpaw.bonaparte.pojos.apiw.DataWithTrackingW;
import de.jpaw.dp.Jdp;
import de.jpaw.dp.Singleton;

/** Utility class which performs the export or data return for SearchRequests which are composed from other requests, or provide
 * the data from in-memory sources.
 */
@Singleton
public class ExporterTool<DTO extends BonaPortable, TRACKING extends TrackingBase> implements IExporterTool<DTO, TRACKING> {

    @Override
    public Long storeAll(OutputSessionParameters op, List<DataWithTrackingW<DTO, TRACKING>> dataList) throws Exception {
        try (IOutputSession outputSession = Jdp.getRequired(IOutputSession.class)) {
            Long sinkRef = outputSession.open(op);
            if (outputSession.getUnwrapTracking(op.getUnwrapTracking())) {
                for (DataWithTrackingW<DTO, TRACKING> data : dataList) {
                    outputSession.store(data.getData());
                }
            } else {
                for (DataWithTrackingW<DTO, TRACKING> data : dataList) {
                    outputSession.store(data);
                }
            }
            // successful close: store ref
            return sinkRef;
        }
    }

    @Override
    public ReadAllResponse<DTO, TRACKING> returnOrExport(final List<DataWithTrackingW<DTO, TRACKING>> dataList, final OutputSessionParameters op) throws Exception {
        final ReadAllResponse<DTO, TRACKING> resp = new ReadAllResponse<DTO, TRACKING>();
        // if a searchOutputTarget has been defined, push the data into it, otherwise return the ReadAllResponse
        if (op == null) {
            resp.setDataList(dataList);
        } else {
            // push output into an outputSession (export it)
            op.setSmartMappingForDataWithTracking(Boolean.TRUE);
            resp.setSinkRef(storeAll(op, dataList));
            resp.setDataList(ImmutableList.<DataWithTrackingW<DTO,TRACKING>>of());
        }
        return resp;
    }
}
