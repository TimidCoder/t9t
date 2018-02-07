package com.arvatosystems.t9t.ssm;

import com.arvatosystems.t9t.base.CrudViewModel;
import com.arvatosystems.t9t.base.IViewModelContainer;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.ssm.request.SchedulerSetupCrudRequest;
import com.arvatosystems.t9t.ssm.request.SchedulerSetupSearchRequest;

public class T9tSsmModels implements IViewModelContainer {
    public static final CrudViewModel<SchedulerSetupDTO, FullTrackingWithVersion> SCHEDULER_SETUP_VIEW_MODEL = new CrudViewModel<SchedulerSetupDTO, FullTrackingWithVersion>(
            SchedulerSetupDTO.BClass.INSTANCE,
            FullTrackingWithVersion.BClass.INSTANCE,
            SchedulerSetupSearchRequest.BClass.INSTANCE,
            SchedulerSetupCrudRequest.BClass.INSTANCE);
    static {
        IViewModelContainer.CRUD_VIEW_MODEL_REGISTRY.putIfAbsent("schedulerSetup",  SCHEDULER_SETUP_VIEW_MODEL);
    }
}
