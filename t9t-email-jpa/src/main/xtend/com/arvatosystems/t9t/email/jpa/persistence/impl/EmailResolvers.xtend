package com.arvatosystems.t9t.email.jpa.persistence.impl

import com.arvatosystems.t9t.annotations.jpa.AllCanAccessGlobalTenant
import com.arvatosystems.t9t.annotations.jpa.AutoResolver42
import com.arvatosystems.t9t.email.EmailAttachmentsKey
import com.arvatosystems.t9t.email.EmailRef
import com.arvatosystems.t9t.email.jpa.entities.EmailAttachmentsEntity
import com.arvatosystems.t9t.email.jpa.entities.EmailEntity
import com.arvatosystems.t9t.email.jpa.entities.EmailModuleCfgEntity

@AutoResolver42
class EmailResolvers {
    @AllCanAccessGlobalTenant
    def EmailModuleCfgEntity      getEmailModuleCfgEntity  (Long key,                boolean onlyActive) {}

    def EmailEntity               getEmailEntity           (EmailRef ref,            boolean onlyActive) {}
    def EmailAttachmentsEntity    getEmailAttachmentsEntity(EmailAttachmentsKey key, boolean onlyActive) {}
}
