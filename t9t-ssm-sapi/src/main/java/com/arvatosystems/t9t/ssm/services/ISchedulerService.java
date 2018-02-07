package com.arvatosystems.t9t.ssm.services;

import com.arvatosystems.t9t.ssm.SchedulerSetupDTO;


public interface ISchedulerService {

    /**
     * This method schedules a job using the underlying service implementation.
     *
     * @param setup information about the job that shall be created
     */
    void createScheduledJob(SchedulerSetupDTO setup);

    /**
     * This method updates a previously scheduled job using the underlying service implementation.
     *
     * @param setup information about the job that shall be updated
     */
    void updateScheduledJob(SchedulerSetupDTO setup);

    /**
     * This method removes a previously schedules job using the underlying service implementation.
     * If "preventive" is set, the job is not expected to exist, and is only deleted to make sure.
     * Any messages about non-existing jobs should be ignored then!
     */
    void removeScheduledJob(String schedulerId);

    /**
     * determine the CRON expression of a given setup
     * @param setup
     * @return
     */
    String determineCronExpression(SchedulerSetupDTO setup);
}
