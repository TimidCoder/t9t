package com.arvatosystems.t9t.base.be.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.arvatosystems.t9t.base.output.OutputSessionParameters;
import com.arvatosystems.t9t.base.search.ReadAllResponse;
import com.arvatosystems.t9t.base.services.IExporterTool2;
import com.arvatosystems.t9t.base.services.IOutputSession;
import com.google.common.collect.ImmutableList;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.pojos.api.TrackingBase;
import de.jpaw.bonaparte.pojos.apiw.DataWithTrackingW;
import de.jpaw.dp.Jdp;
import de.jpaw.dp.Singleton;

/** Utility class which performs the export or data return for SearchRequests which are composed from other requests, with DTO extensions / post processing.
 */
@Singleton
public class ExporterTool2<DTO extends BonaPortable, EXTDTO extends DTO, TRACKING extends TrackingBase> implements IExporterTool2<DTO, EXTDTO, TRACKING> {

    @Override
    public Long storeAll(OutputSessionParameters op, List<DataWithTrackingW<DTO, TRACKING>> dataList, Function<DTO, EXTDTO> converter) throws Exception {
        try (IOutputSession outputSession = Jdp.getRequired(IOutputSession.class)) {
            Long sinkRef = outputSession.open(op);
            for (DataWithTrackingW<DTO, TRACKING> data : dataList) {
                EXTDTO extDto = converter.apply(data.getData());
                if (extDto != null) {
                    data.setData(extDto);  // replace the data with the extended one
                }
                outputSession.store(data);
            }
            // successful close: store ref
            return sinkRef;
        }
    }

    @Override
    public ReadAllResponse<EXTDTO, TRACKING> returnOrExport(final List<DataWithTrackingW<DTO, TRACKING>> dataList, final OutputSessionParameters op, Function<DTO, EXTDTO> converter) throws Exception {
        final ReadAllResponse<EXTDTO, TRACKING> resp = new ReadAllResponse<EXTDTO, TRACKING>();
        // if a searchOutputTarget has been defined, push the data into it, otherwise return the ReadAllResponse
        if (op == null) {
            // conversion of the data list is required
            final List<DataWithTrackingW<EXTDTO, TRACKING>> extDataList = new ArrayList<DataWithTrackingW<EXTDTO, TRACKING>>(dataList.size());
            for (DataWithTrackingW<DTO, TRACKING> data : dataList) {
                EXTDTO extDto = converter.apply(data.getData());
                if (extDto != null) {
                    data.setData(extDto);  // replace the data with the extended one
                    extDataList.add((DataWithTrackingW<EXTDTO, TRACKING>) data); // do a cast instead of new allocation, because that supports extensions of DataWithTrackingW as well
                }
            }
            resp.setDataList(extDataList);
        } else {
            // push output into an outputSession (export it)
            op.setSmartMappingForDataWithTracking(Boolean.TRUE);
            resp.setSinkRef(storeAll(op, dataList, converter));
            resp.setDataList(ImmutableList.<DataWithTrackingW<EXTDTO,TRACKING>>of());
        }
        return resp;
    }
}
