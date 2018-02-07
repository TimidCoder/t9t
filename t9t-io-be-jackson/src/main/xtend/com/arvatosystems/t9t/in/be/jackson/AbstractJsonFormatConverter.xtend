package com.arvatosystems.t9t.in.be.jackson

import com.arvatosystems.t9t.in.services.IInputSession
import com.arvatosystems.t9t.io.DataSinkDTO
import com.arvatosystems.t9t.server.services.IStatefulServiceSession
import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.ObjectMapper
import de.jpaw.annotations.AddLogger
import de.jpaw.bonaparte.core.BonaPortableClass
import java.util.Map
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.arvatosystems.t9t.in.be.impl.AbstractInputFormatConverter

@AddLogger
abstract class AbstractJsonFormatConverter extends AbstractInputFormatConverter {
    protected ObjectMapper objectMapper
    protected JsonFactory factory
    override open(IInputSession inputSession, DataSinkDTO sinkCfg, IStatefulServiceSession session, Map<String, Object> params, BonaPortableClass<?> baseBClass) {
        super.open(inputSession, sinkCfg, session, params, baseBClass)
        objectMapper = new ObjectMapper()
        objectMapper.registerModule(new JodaModule());
        factory = objectMapper.factory
    }
}
