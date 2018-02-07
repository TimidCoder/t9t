package com.arvatosystems.t9t.base.services;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import com.arvatosystems.t9t.base.search.AbstractExportRequest;
import com.arvatosystems.t9t.base.search.SinkCreatedResponse;

public interface IOutputSessionExporter {
    public <D> SinkCreatedResponse runExport(
            final AbstractExportRequest request,
            final String defaultDataSinkId,
            final Map<String, Object> params,  // optional
            final BiFunction<Long, Integer, List<D>> chunkReader,
            final BiFunction<List<D>, IOutputSession, Long> chunkWriter,  // alternative to writer
            final BiFunction<D, IOutputSession, Long> writer);
}
