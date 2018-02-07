package com.arvatosystems.t9t.email.services;

import java.util.UUID;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.email.EmailModuleCfgDTO;
import com.arvatosystems.t9t.email.api.EmailMessage;

/** API between the request handler and the internal implementations of the low level sender.
 * In case of issues, the sender returns a ServiceResponse which outlines the error code and any details.
 *
 * Implementations are queried by JDP qualifier.
 *
 */
public interface IEmailSender {
    ServiceResponse sendEmail(
            Long messageRef,
            UUID messageId,
            EmailMessage msg,
            EmailModuleCfgDTO configuration);
}
