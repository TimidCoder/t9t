package com.arvatosystems.t9t.uiprefs;

import com.arvatosystems.t9t.base.CrudViewModel;
import com.arvatosystems.t9t.base.IViewModelContainer;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.uiprefsv3.LeanGridConfigDTO;
import com.arvatosystems.t9t.uiprefsv3.request.LeanGridConfigCrudRequest;
import com.arvatosystems.t9t.uiprefsv3.request.LeanGridConfigSearchRequest;

public class T9tUiprefsModels implements IViewModelContainer {
    public static final CrudViewModel<LeanGridConfigDTO, FullTrackingWithVersion> LEAN_GRID_CONFIG_VIEW_MODEL = new CrudViewModel<LeanGridConfigDTO, FullTrackingWithVersion>(
        LeanGridConfigDTO.BClass.INSTANCE,
        FullTrackingWithVersion.BClass.INSTANCE,
        LeanGridConfigSearchRequest.BClass.INSTANCE,
        LeanGridConfigCrudRequest.BClass.INSTANCE);

    static {
        IViewModelContainer.CRUD_VIEW_MODEL_REGISTRY.putIfAbsent("leanGridConfig",        LEAN_GRID_CONFIG_VIEW_MODEL);
    }
}
