package com.arvatosystems.t9t.rep.services;

import java.util.Map;

import com.arvatosystems.t9t.base.output.OutputSessionParameters;
import com.arvatosystems.t9t.rep.ReportParamsDTO;

public interface IJasperParameterEnricher {
    void enrichParameter(Map<String, Object> parameters, ReportParamsDTO reportParamsDTO,
            Map<String, Object> outputSessionAdditionalParametersList,
            OutputSessionParameters outputSessionParameters
    );
}
