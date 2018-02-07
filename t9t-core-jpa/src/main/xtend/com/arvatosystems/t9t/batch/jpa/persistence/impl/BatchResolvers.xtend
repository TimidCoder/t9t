package com.arvatosystems.t9t.batch.jpa.persistence.impl

import com.arvatosystems.t9t.annotations.jpa.AutoResolver42
import com.arvatosystems.t9t.batch.SliceTrackingRef
import com.arvatosystems.t9t.batch.StatisticsRef
import com.arvatosystems.t9t.batch.jpa.entities.SliceTrackingEntity
import com.arvatosystems.t9t.batch.jpa.entities.StatisticsEntity

@AutoResolver42
class BatchResolvers {
    def StatisticsEntity        getStatisticsEntity   (StatisticsRef    entityRef, boolean onlyActive) { return null; }
    def SliceTrackingEntity     getSliceTrackingEntity(SliceTrackingRef entityRef, boolean onlyActive) { return null; }
    def SliceTrackingEntity     findByDataSinkIdAndId(boolean onlyActive, String dataSinkId, String id) {null}
}
