package com.arvatosystems.t9t.authc.be.api

import com.arvatosystems.t9t.auth.UserDTO
import com.arvatosystems.t9t.auth.services.IUserResolver
import com.arvatosystems.t9t.authc.api.GetUserDataResponse
import com.arvatosystems.t9t.base.api.RequestParameters
import com.arvatosystems.t9t.base.services.AbstractReadOnlyRequestHandler
import de.jpaw.dp.Inject
import de.jpaw.bonaparte.annotations.FieldMapper
import com.arvatosystems.t9t.authc.api.UserData

class AbstractGetUserDataRequestHandler<T extends RequestParameters> extends AbstractReadOnlyRequestHandler<T> {
    @Inject
    protected IUserResolver resolver

    @FieldMapper
    def protected void mapUserData(UserData data, UserDTO dto) {
    }

    def protected GetUserDataResponse responseFromDto(UserDTO dto) {
        val resp = new GetUserDataResponse
        val data = new UserData
        mapUserData(data, dto)
        data.userRef = dto.objectRef
        resp.userData = data
        return resp
    }
}
