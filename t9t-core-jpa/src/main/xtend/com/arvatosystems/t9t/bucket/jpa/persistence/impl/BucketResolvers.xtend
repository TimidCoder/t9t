package com.arvatosystems.t9t.bucket.jpa.persistence.impl

import com.arvatosystems.t9t.annotations.jpa.AutoResolver42
import com.arvatosystems.t9t.bucket.BucketCounterRef
import com.arvatosystems.t9t.bucket.BucketEntryKey
import com.arvatosystems.t9t.bucket.jpa.entities.BucketCounterEntity
import com.arvatosystems.t9t.bucket.jpa.entities.BucketEntryEntity

@AutoResolver42
class BucketResolvers {
    def BucketCounterEntity           getBucketCounterEntity(BucketCounterRef key, boolean onlyActive) { return null; }
    def BucketCounterEntity           findByQualifier       (boolean onlyActive,   String qualifier)   { return null; }
    def BucketEntryEntity             getBucketEntryEntity  (BucketEntryKey   key, boolean onlyActive) { return null; }
}
