package com.arvatosystems.t9t.server.services;

import java.io.Closeable;

import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.server.ExecutionSummary;
import com.arvatosystems.t9t.server.InternalHeaderParameters;

// used by the central request dispatcher
public interface IRequestLogger extends Closeable {
    void open();        // first call in regular lifecycle
    void logRequest(InternalHeaderParameters hdr, ExecutionSummary summary, RequestParameters params, ServiceResponse response);
    void flush();       // flush unwritten buffers
    @Override
    void close();       // shut down the message writer  last call in regular lifecycle
}
