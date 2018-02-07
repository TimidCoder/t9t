package com.arvatosystems.t9t.bucket.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.bucket.BucketCounterDTO
import com.arvatosystems.t9t.bucket.jpa.entities.BucketCounterEntity
import com.arvatosystems.t9t.bucket.jpa.persistence.IBucketCounterEntityResolver

@AutoMap42
class BucketCounterEntityMappers {
    IBucketCounterEntityResolver entityResolver

    @AutoHandler("CS42")
    def void e2dBucketCounterDTO(BucketCounterEntity entity, BucketCounterDTO dto) {}
}
