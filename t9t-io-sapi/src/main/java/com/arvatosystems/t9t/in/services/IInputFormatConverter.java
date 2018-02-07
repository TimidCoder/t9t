package com.arvatosystems.t9t.in.services;

import java.io.InputStream;
import java.util.Map;

import com.arvatosystems.t9t.io.DataSinkDTO;
import com.arvatosystems.t9t.server.services.IStatefulServiceSession;

import de.jpaw.bonaparte.core.BonaPortableClass;

// implementations must be thread safe for method transform
@FunctionalInterface
public interface IInputFormatConverter {
    /** Performs any optional initial output. */
    default void open(IInputSession inputSession, DataSinkDTO cfg, IStatefulServiceSession session, Map<String, Object> params,
            BonaPortableClass<?> baseBClass) {
        // no action required in simple cases
    }

    /** Processes a whole stream of data. Should invoke inutSession.setHeaderData() per header field and inputSession.process(BonaPortable)
     * per data record. */
    void process(InputStream is);

    /** Ends processing, writes a summary into the sink table. */
    default void close() {
        // no action required in simple cases
    }
}
