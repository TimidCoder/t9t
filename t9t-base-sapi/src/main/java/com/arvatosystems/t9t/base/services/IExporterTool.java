package com.arvatosystems.t9t.base.services;

import java.util.List;

import com.arvatosystems.t9t.base.output.OutputSessionParameters;
import com.arvatosystems.t9t.base.search.ReadAllResponse;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.pojos.api.TrackingBase;
import de.jpaw.bonaparte.pojos.apiw.DataWithTrackingW;

public interface IExporterTool<DTO extends BonaPortable, TRACKING extends TrackingBase> {
    /** Opens an output session, pushes all data into it, and returns the generated sinkRef.
     * @throws Exception */
    Long storeAll(OutputSessionParameters op, List<DataWithTrackingW<DTO, TRACKING>> dataList) throws Exception;

    ReadAllResponse<DTO, TRACKING> returnOrExport(List<DataWithTrackingW<DTO, TRACKING>> dataList, OutputSessionParameters op) throws Exception;
}
