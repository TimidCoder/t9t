package com.arvatosystems.t9t.auth;

import java.util.Arrays;
import java.util.List;

import com.arvatosystems.t9t.base.ILeanGridConfigContainer;

public class T9tAuthLeanGridConfig implements ILeanGridConfigContainer {

    private static String [] GRID_CONFIGS = {
        "sessions",
        "user",
        "tenant",
        "role",
        "apikey",
        "userTenantRole",
        "roleToPermission",
        "authModuleCfg",
        "tenantLogo"
    };

    @Override
    public List<String> getResourceNames() {
        return Arrays.asList(GRID_CONFIGS);
    }
}
