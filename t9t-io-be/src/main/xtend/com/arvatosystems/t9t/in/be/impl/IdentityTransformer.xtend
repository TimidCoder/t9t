package com.arvatosystems.t9t.in.be.impl

import com.arvatosystems.t9t.base.api.RequestParameters
import com.arvatosystems.t9t.base.request.ErrorRequest
import com.arvatosystems.t9t.io.T9tIOException
import de.jpaw.bonaparte.core.BonaPortable

/** Implementation which just validates that the received data is of type RequestParameters. */
class IdentityTransformer extends AbstractInputDataTransformer<BonaPortable> {

    override transform(BonaPortable dto) {
        if (baseBClass !== null && !baseBClass.bonaPortableClass.isAssignableFrom(dto.class)) {
            // as no transformer exists, any parser object type is also a processor object type
            return new ErrorRequest => [
                returnCode = T9tIOException.WRONG_RECORD_TYPE
                errorDetails    = baseBClass.class.simpleName
            ]
        }
        if (dto instanceof RequestParameters) {
            return dto
        }
        return new ErrorRequest => [
            returnCode = T9tIOException.WRONG_RECORD_TYPE
            errorDetails    = "RequestParameters"
        ]
    }
}
