package com.arvatosystems.t9t.statistics.services;

import com.arvatosystems.t9t.batch.StatisticsDTO;

/**
 * Service for handling statistics data.
 *
 * @author greg
 *
 */
public interface IStatisticsService {

    /**
     * Allows to store statistics data.
     *
     * @param data
     *            data to store
     */
    public void saveStatisticsData(StatisticsDTO data);

}
