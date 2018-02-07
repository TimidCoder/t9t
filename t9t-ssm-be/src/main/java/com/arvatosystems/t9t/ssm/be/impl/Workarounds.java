package com.arvatosystems.t9t.ssm.be.impl;

import com.arvatosystems.t9t.base.be.impl.CrossModuleRefResolver;
import com.arvatosystems.t9t.core.CannedRequestDTO;
import com.arvatosystems.t9t.core.CannedRequestRef;
import com.arvatosystems.t9t.core.request.CannedRequestCrudRequest;

public class Workarounds {
    public static CannedRequestDTO getData(CrossModuleRefResolver refResolver, CannedRequestRef ref) {
        return refResolver.getData(new CannedRequestCrudRequest(), ref);
    }
}
