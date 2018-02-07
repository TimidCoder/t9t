package com.arvatosystems.t9t.base.services;

import java.util.List;
import java.util.function.Function;

import com.arvatosystems.t9t.base.output.OutputSessionParameters;
import com.arvatosystems.t9t.base.search.ReadAllResponse;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.pojos.api.TrackingBase;
import de.jpaw.bonaparte.pojos.apiw.DataWithTrackingW;

/** An extended version of the exporter tool, which can convert DTOs. */
public interface IExporterTool2<DTO extends BonaPortable, EXTDTO extends DTO, TRACKING extends TrackingBase> {
    /** Opens an output session, pushes all data into it, and returns the generated sinkRef.
     * @throws Exception */
    Long storeAll(OutputSessionParameters op, List<DataWithTrackingW<DTO, TRACKING>> dataList, Function<DTO, EXTDTO> converter) throws Exception;

    ReadAllResponse<EXTDTO, TRACKING> returnOrExport(List<DataWithTrackingW<DTO, TRACKING>> dataList, OutputSessionParameters op, Function<DTO, EXTDTO> converter) throws Exception;
}
