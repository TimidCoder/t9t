package com.arvatosystems.t9t.batch.jpa.impl;

import com.arvatosystems.t9t.base.entities.WriteTracking;
import com.arvatosystems.t9t.base.jpa.impl.AbstractJpaResolver;
import com.arvatosystems.t9t.batch.StatisticsDTO;
import com.arvatosystems.t9t.batch.StatisticsRef;
import com.arvatosystems.t9t.batch.jpa.entities.StatisticsEntity;
import com.arvatosystems.t9t.batch.jpa.mapping.IStatisticsDTOMapper;
import com.arvatosystems.t9t.batch.jpa.persistence.IStatisticsEntityResolver;
import com.arvatosystems.t9t.batch.services.IStatisticsResolver;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Singleton;

@Singleton
public class StatisticsResolver extends AbstractJpaResolver<StatisticsRef, StatisticsDTO, WriteTracking, StatisticsEntity> implements IStatisticsResolver {

    public StatisticsResolver() {
        super("Statistics", Jdp.getRequired(IStatisticsEntityResolver.class), Jdp.getRequired(IStatisticsDTOMapper.class));
    }

    @Override
    public StatisticsRef createKey(Long ref) {
        return ref == null ? null : new StatisticsRef(ref);
    }
}
