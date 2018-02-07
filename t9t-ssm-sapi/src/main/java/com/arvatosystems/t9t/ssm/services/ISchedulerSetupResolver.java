package com.arvatosystems.t9t.ssm.services;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.ssm.SchedulerSetupDTO;
import com.arvatosystems.t9t.ssm.SchedulerSetupRef;

import de.jpaw.bonaparte.refsw.RefResolver;

public interface ISchedulerSetupResolver extends RefResolver<SchedulerSetupRef, SchedulerSetupDTO, FullTrackingWithVersion> {}
