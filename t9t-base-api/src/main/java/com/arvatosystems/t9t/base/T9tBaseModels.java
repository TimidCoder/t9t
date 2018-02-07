package com.arvatosystems.t9t.base;

import com.arvatosystems.t9t.base.output.ExportParameters;

import de.jpaw.bonaparte.pojos.api.TrackingBase;

public class T9tBaseModels implements IViewModelContainer {

    public static final CrudViewModel<ExportParameters, TrackingBase> EXPORT_PARAMS_VIEW_MODEL
      = new CrudViewModel<ExportParameters, TrackingBase>(ExportParameters.BClass.INSTANCE, null, null, null);

    static {
        IViewModelContainer.CRUD_VIEW_MODEL_REGISTRY.putIfAbsent("exportParams",  EXPORT_PARAMS_VIEW_MODEL);
    }
}
