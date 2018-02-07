package com.arvatosystems.t9t.email.services;

import java.util.UUID;

import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.email.EmailModuleCfgDTO;
import com.arvatosystems.t9t.email.api.EmailMessage;

/** Defines the communication layer between the backend modules (business logic / persistence layer). */
public interface IEmailPersistenceAccess {
    public static final EmailModuleCfgDTO DEFAULT_MODULE_CFG = new EmailModuleCfgDTO(
    );

    // MediaData               getEmailAttachment    (Long emailRef, Integer attachmentNo);
    // List<MediaData>         getEmailAttachments   (Long emailRef);
    void                    persistEmail(long emailRef, UUID messageId, RequestContext ctx, EmailMessage msg, boolean sendSpooled, boolean storeEmail);
}
