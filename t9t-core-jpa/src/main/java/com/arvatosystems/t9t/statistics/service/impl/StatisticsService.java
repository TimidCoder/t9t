package com.arvatosystems.t9t.statistics.service.impl;

import com.arvatosystems.t9t.batch.StatisticsDTO;
import com.arvatosystems.t9t.batch.jpa.entities.StatisticsEntity;
import com.arvatosystems.t9t.batch.jpa.mapping.IStatisticsDTOMapper;
import com.arvatosystems.t9t.batch.jpa.persistence.IStatisticsEntityResolver;
import com.arvatosystems.t9t.statistics.services.IStatisticsService;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Singleton;

/**
 * Default implementation of {@linkplain IStatisticsService}.
 *
 * @author greg
 *
 */
@Singleton
public class StatisticsService implements IStatisticsService {

    protected final IStatisticsEntityResolver statisticsEntityResolver = Jdp.getRequired(IStatisticsEntityResolver.class);
    protected final IStatisticsDTOMapper statisticsDataDTOMappers = Jdp.getRequired(IStatisticsDTOMapper.class);

    @Override
    public void saveStatisticsData(StatisticsDTO data) {
        StatisticsEntity entity = statisticsDataDTOMappers.mapToEntity(data, true);
        entity.setObjectRef(statisticsEntityResolver.createNewPrimaryKey());
        statisticsEntityResolver.save(entity);
    }
}
