package com.arvatosystems.t9t.bucket.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.bucket.BucketEntryDTO
import com.arvatosystems.t9t.bucket.jpa.entities.BucketEntryEntity
import com.arvatosystems.t9t.bucket.jpa.persistence.IBucketEntryEntityResolver

@AutoMap42
class BucketEntryEntityMappers {
    IBucketEntryEntityResolver entityResolver

    @AutoHandler("CS42")
    def void e2dBucketEntryDTO(BucketEntryEntity entity, BucketEntryDTO dto) {}
}
