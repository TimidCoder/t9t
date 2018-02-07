package com.arvatosystems.t9t.rep.services;

import com.arvatosystems.t9t.rep.ReportConfigDTO;
import com.arvatosystems.t9t.rep.ReportConfigRef;
import com.arvatosystems.t9t.rep.ReportParamsDTO;
import com.arvatosystems.t9t.rep.ReportParamsRef;

/** Defines the methods used to interact between the BE and the JPA module. */
public interface IRepPersistenceAccess {

    /** Returns a report config DTO. Shortcut for the cross module resolver. */
    ReportConfigDTO getConfigDTO(ReportConfigRef configRef) throws Exception;

    /** Returns a report params DTO. Shortcut for the cross module resolver. */
    ReportParamsDTO getParamsDTO(ReportParamsRef configRef) throws Exception;

}
