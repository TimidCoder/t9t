package com.arvatosystems.t9t.server.services;

import java.util.List;

import com.arvatosystems.t9t.base.auth.PermissionEntry;
import com.arvatosystems.t9t.base.auth.PermissionType;

import de.jpaw.bonaparte.pojos.api.OperationType;
import de.jpaw.bonaparte.pojos.api.auth.JwtInfo;
import de.jpaw.bonaparte.pojos.api.auth.Permissionset;

public interface IAuthorize {
    public static final Permissionset NO_PERMISSIONS = new Permissionset();
    public static final Permissionset EXEC_PERMISSION = new Permissionset(1 << OperationType.EXECUTE.ordinal());
    public static final Permissionset ALL_PERMISSIONS = new Permissionset(0xfffff);  // 20 permission bits all set

    Permissionset getPermissions(JwtInfo jwtInfo, PermissionType permissionType, String resource);
    List<PermissionEntry> getAllPermissions(JwtInfo jwtInfo, PermissionType permissionType);
}
