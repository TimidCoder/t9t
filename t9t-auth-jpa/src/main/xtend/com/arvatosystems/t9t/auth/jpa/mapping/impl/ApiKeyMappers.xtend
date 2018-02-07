package com.arvatosystems.t9t.auth.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.annotations.jpa.NeedMapping
import com.arvatosystems.t9t.auth.ApiKeyDTO
import com.arvatosystems.t9t.auth.jpa.entities.ApiKeyEntity
import com.arvatosystems.t9t.auth.jpa.mapping.IRoleDTOMapper
import com.arvatosystems.t9t.auth.jpa.mapping.IUserDTOMapper
import com.arvatosystems.t9t.auth.jpa.persistence.IApiKeyEntityResolver
import com.arvatosystems.t9t.auth.jpa.persistence.IRoleEntityResolver
import com.arvatosystems.t9t.auth.jpa.persistence.IUserEntityResolver

@AutoMap42
public class ApiKeyMappers {
    IApiKeyEntityResolver   resolver
    IRoleEntityResolver     roleResolver
    IRoleDTOMapper          roleMapper
    IUserEntityResolver     userResolver
    IUserDTOMapper          userMapper

//    @AutoHandler("S42")
    @NeedMapping  // required because the DTO is final
    def void e2dApiKeyDTO(ApiKeyEntity entity, ApiKeyDTO dto) {
        dto.roleRef = roleMapper.mapToDto(entity.roleRef)
        dto.userRef = userMapper.mapToDto(entity.userRef)
    }

    @NeedMapping  // required because the DTO is final
    def void d2eApiKeyDTO(ApiKeyEntity entity, ApiKeyDTO dto, boolean onlyActive) {
        entity.roleRef = roleResolver.getRef(dto.roleRef, onlyActive)
        entity.userRef = userResolver.getRef(dto.userRef, onlyActive)
    }
}
