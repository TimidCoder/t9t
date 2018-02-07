package com.arvatosystems.t9t.auth.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.auth.UserTenantRoleDTO
import com.arvatosystems.t9t.auth.jpa.entities.UserTenantRoleEntity
import com.arvatosystems.t9t.auth.jpa.mapping.IRoleDTOMapper
import com.arvatosystems.t9t.auth.jpa.mapping.IUserDescriptionMapper
import com.arvatosystems.t9t.auth.jpa.persistence.IRoleEntityResolver
import com.arvatosystems.t9t.auth.jpa.persistence.IUserEntityResolver
import com.arvatosystems.t9t.auth.jpa.persistence.IUserTenantRoleEntityResolver

@AutoMap42
public class UserTenantRoleMappers {
    IUserTenantRoleEntityResolver entityResolver
    IUserDescriptionMapper userMapper
    IRoleDTOMapper roleMapper
    IUserEntityResolver userResolver
    IRoleEntityResolver roleResolver

    def void d2eUserTenantRoleDTO(UserTenantRoleEntity entity, UserTenantRoleDTO it, boolean onlyActive) {
        entity.userRef = userResolver.getRef(userRef, onlyActive)
        entity.roleRef = roleResolver.getRef(roleRef, onlyActive)
    }

    @AutoHandler("P42")
    def void e2dUserTenantRoleDTO(UserTenantRoleEntity it, UserTenantRoleDTO dto) {
        dto.userRef = userMapper.mapToDto(userRef)
        dto.roleRef = roleMapper.mapToDto(roleRef)
    }
}
