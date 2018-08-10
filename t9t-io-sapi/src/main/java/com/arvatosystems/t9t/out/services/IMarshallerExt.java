package com.arvatosystems.t9t.out.services;

import de.jpaw.bonaparte.util.IMarshaller;

public interface IMarshallerExt<R> extends IMarshaller {
    /** Extract a logical return code from a client's response. */
    default Integer getClientReturnCode(R response) { return null; };

    /** Extract a logical object or method reference from a client's response. */
    default String getClientReference(R response) { return null; };
}
