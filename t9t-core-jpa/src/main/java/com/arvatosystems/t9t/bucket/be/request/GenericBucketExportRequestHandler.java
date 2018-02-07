package com.arvatosystems.t9t.bucket.be.request;

import java.util.List;
import java.util.Map;

import com.arvatosystems.t9t.base.services.IOutputSession;
import com.arvatosystems.t9t.bucket.request.GenericBucketExportRequest;
import com.arvatosystems.t9t.bucket.services.IBucketEntryMapper;

import de.jpaw.dp.Jdp;

public class GenericBucketExportRequestHandler extends AbstractBucketExportRequestHandler<GenericBucketExportRequest> {

    @Override
    void exportChunk(IOutputSession os, List<Long> refs, GenericBucketExportRequest request, String qualifier, int bucketNoToSelect) {
        IBucketEntryMapper mapper = Jdp.getRequired(IBucketEntryMapper.class, request.getQualifier());
        boolean withMore = request.getWithTrackingAndMore();

        // expands refs to Entries
        Map<Long, Integer> entries = withMore || mapper.alwaysNeedModes() ? super.getModes(qualifier, bucketNoToSelect, refs) : null;
        mapper.writeEntities(refs, entries, withMore, os);
    }
}
