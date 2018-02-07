package com.arvatosystems.t9t.email;

import com.arvatosystems.t9t.base.CrudViewModel;
import com.arvatosystems.t9t.base.IViewModelContainer;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.email.request.EmailCrudRequest;
import com.arvatosystems.t9t.email.request.EmailModuleCfgCrudRequest;
import com.arvatosystems.t9t.email.request.EmailModuleCfgSearchRequest;
import com.arvatosystems.t9t.email.request.EmailSearchRequest;

public class T9tEmailModels implements IViewModelContainer {
    public static final CrudViewModel<EmailDTO, FullTrackingWithVersion> EMAIL_LOG_VIEW_MODEL = new CrudViewModel<EmailDTO, FullTrackingWithVersion>(
        EmailDTO.BClass.INSTANCE,
        FullTrackingWithVersion.BClass.INSTANCE,
        EmailSearchRequest.BClass.INSTANCE,
        EmailCrudRequest.BClass.INSTANCE);
    public static final CrudViewModel<EmailModuleCfgDTO, FullTrackingWithVersion> EMAIL_MODULE_CFG_VIEW_MODEL = new CrudViewModel<EmailModuleCfgDTO, FullTrackingWithVersion>(
        EmailModuleCfgDTO.BClass.INSTANCE,
        FullTrackingWithVersion.BClass.INSTANCE,
        EmailModuleCfgSearchRequest.BClass.INSTANCE,
        EmailModuleCfgCrudRequest.BClass.INSTANCE);

    static {
        IViewModelContainer.CRUD_VIEW_MODEL_REGISTRY.putIfAbsent("emailLog",  EMAIL_LOG_VIEW_MODEL);
        IViewModelContainer.CRUD_VIEW_MODEL_REGISTRY.putIfAbsent("emailModuleCfg",  EMAIL_MODULE_CFG_VIEW_MODEL);
    }
}
