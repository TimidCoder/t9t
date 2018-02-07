package com.arvatosystems.t9t.batch.services;

import com.arvatosystems.t9t.base.entities.WriteTracking;
import com.arvatosystems.t9t.batch.StatisticsDTO;
import com.arvatosystems.t9t.batch.StatisticsRef;

import de.jpaw.bonaparte.refsw.RefResolver;

public interface IStatisticsResolver extends RefResolver<StatisticsRef, StatisticsDTO, WriteTracking> {}
