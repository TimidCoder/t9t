package com.arvatosystems.t9t.auth.jpa;

import com.arvatosystems.t9t.auth.jpa.entities.UserEntity;
import com.arvatosystems.t9t.base.services.RequestContext;

public interface IPasswordSettingService {
    void setPasswordForUser(RequestContext ctx, UserEntity user, String password);
}
