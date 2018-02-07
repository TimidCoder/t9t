package com.arvatosystems.t9t.msglog;

import com.arvatosystems.t9t.base.CrudViewModel;
import com.arvatosystems.t9t.base.IViewModelContainer;
import com.arvatosystems.t9t.msglog.request.MessageSearchRequest;

import de.jpaw.bonaparte.pojos.api.NoTracking;

public class T9tMsglogModels implements IViewModelContainer {
    public static final CrudViewModel<MessageDTO, NoTracking> MESSAGE_VIEW_MODEL = new CrudViewModel<MessageDTO, NoTracking>(
        MessageDTO.BClass.INSTANCE,
        NoTracking.BClass.INSTANCE,
        MessageSearchRequest.BClass.INSTANCE,
        null);

    static {
        IViewModelContainer.CRUD_VIEW_MODEL_REGISTRY.putIfAbsent("requests", MESSAGE_VIEW_MODEL);
    }
}
