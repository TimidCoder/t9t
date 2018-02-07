package com.arvatosystems.t9t.auth.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.auth.RoleToPermissionDTO
import com.arvatosystems.t9t.auth.jpa.entities.RoleToPermissionEntity
import com.arvatosystems.t9t.auth.jpa.mapping.IRoleDTOMapper
import com.arvatosystems.t9t.auth.jpa.persistence.IRoleEntityResolver
import com.arvatosystems.t9t.auth.jpa.persistence.IRoleToPermissionEntityResolver

@AutoMap42
public class RoleToPermissionMappers {
    IRoleToPermissionEntityResolver entityResolver
    IRoleDTOMapper roleMapper
    IRoleEntityResolver roleResolver

    def void d2eRoleToPermissionDTO(RoleToPermissionEntity entity, RoleToPermissionDTO it, boolean onlyActive) {
        entity.roleRef = roleResolver.getRef(roleRef, onlyActive)
    }

    @AutoHandler("SP42")
    def void e2dRoleToPermissionDTO(RoleToPermissionEntity it, RoleToPermissionDTO dto) {
        dto.roleRef = roleMapper.mapToDto(roleRef)
    }
}
