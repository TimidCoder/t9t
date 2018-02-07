package com.arvatosystems.t9t.email.be.stubs

import com.arvatosystems.t9t.email.EmailModuleCfgDTO
import com.arvatosystems.t9t.email.api.EmailMessage
import com.arvatosystems.t9t.email.services.IEmailSender
import de.jpaw.annotations.AddLogger
import de.jpaw.dp.Named
import de.jpaw.dp.Singleton
import java.util.UUID

/** Implementation of IEmailSender using a /dev/null (stub). */
@AddLogger
@Singleton
@Named("NULL")
class CommonsEmailService implements IEmailSender {

    override sendEmail(Long messageRef, UUID messageId, EmailMessage msg, EmailModuleCfgDTO configuration) {
        LOGGER.info("Not sending email ref {}, ID {} to {} (stub NULL configured)", messageRef, messageId, msg.recipient.to.get(0))
        return null
    }
}
