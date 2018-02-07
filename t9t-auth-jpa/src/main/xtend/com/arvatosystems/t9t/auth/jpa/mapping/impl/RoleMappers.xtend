package com.arvatosystems.t9t.auth.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.auth.RoleDTO
import com.arvatosystems.t9t.auth.jpa.entities.RoleEntity
import com.arvatosystems.t9t.auth.jpa.persistence.IRoleEntityResolver

@AutoMap42
public class RoleMappers {
    IRoleEntityResolver resolver
//    @AutoHandler("S42")   uses the sapi BE search
    def void e2dRoleDTO(RoleEntity entity, RoleDTO dto) {}
}
