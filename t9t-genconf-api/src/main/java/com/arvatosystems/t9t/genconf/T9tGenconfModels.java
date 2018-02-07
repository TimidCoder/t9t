package com.arvatosystems.t9t.genconf;

import com.arvatosystems.t9t.base.CrudViewModel;
import com.arvatosystems.t9t.base.IViewModelContainer;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.genconf.request.ConfigCrudRequest;
import com.arvatosystems.t9t.genconf.request.ConfigSearchRequest;

public class T9tGenconfModels implements IViewModelContainer {
    public static final CrudViewModel<ConfigDTO, FullTrackingWithVersion> GENERIC_CONFIG_VIEW_MODEL = new CrudViewModel<ConfigDTO, FullTrackingWithVersion>(
        ConfigDTO.BClass.INSTANCE,
        FullTrackingWithVersion.BClass.INSTANCE,
        ConfigSearchRequest.BClass.INSTANCE,
        ConfigCrudRequest.BClass.INSTANCE);

    static {
        IViewModelContainer.CRUD_VIEW_MODEL_REGISTRY.putIfAbsent("genericConfig",        GENERIC_CONFIG_VIEW_MODEL);
    }
}
