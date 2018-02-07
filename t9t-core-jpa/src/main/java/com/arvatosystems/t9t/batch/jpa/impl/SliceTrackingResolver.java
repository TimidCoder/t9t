package com.arvatosystems.t9t.batch.jpa.impl;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.jpa.impl.AbstractJpaResolver;
import com.arvatosystems.t9t.batch.SliceTrackingDTO;
import com.arvatosystems.t9t.batch.SliceTrackingRef;
import com.arvatosystems.t9t.batch.jpa.entities.SliceTrackingEntity;
import com.arvatosystems.t9t.batch.jpa.mapping.ISliceTrackingDTOMapper;
import com.arvatosystems.t9t.batch.jpa.persistence.ISliceTrackingEntityResolver;
import com.arvatosystems.t9t.batch.services.ISliceTrackingResolver;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Singleton;

@Singleton
public class SliceTrackingResolver extends AbstractJpaResolver<SliceTrackingRef, SliceTrackingDTO, FullTrackingWithVersion, SliceTrackingEntity> implements ISliceTrackingResolver {

    public SliceTrackingResolver() {
        super("SliceTracking", Jdp.getRequired(ISliceTrackingEntityResolver.class), Jdp.getRequired(ISliceTrackingDTOMapper.class));
    }

    @Override
    public SliceTrackingRef createKey(Long ref) {
        return ref == null ? null : new SliceTrackingRef(ref);
    }
}
