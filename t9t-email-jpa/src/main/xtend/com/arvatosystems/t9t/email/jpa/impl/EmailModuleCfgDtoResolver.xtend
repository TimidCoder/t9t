package com.arvatosystems.t9t.email.jpa.impl

import com.arvatosystems.t9t.core.jpa.impl.AbstractModuleConfigResolver
import com.arvatosystems.t9t.email.EmailModuleCfgDTO
import com.arvatosystems.t9t.email.jpa.entities.EmailModuleCfgEntity
import com.arvatosystems.t9t.email.jpa.persistence.IEmailModuleCfgEntityResolver
import com.arvatosystems.t9t.email.services.IEmailModuleCfgDtoResolver
import de.jpaw.dp.Singleton

@Singleton
class EmailModuleCfgDtoResolver extends AbstractModuleConfigResolver<EmailModuleCfgDTO, EmailModuleCfgEntity> implements IEmailModuleCfgDtoResolver {
    private static final EmailModuleCfgDTO DEFAULT_MODULE_CFG = new EmailModuleCfgDTO(
        null,                       // Json z
        "SMTP",                     // implementation:             currently supported: SMTP, SES, VERTX
        "smtp",                     // smtpServerTransport         default to "smtp"
        "cmail.servicemail24.de",   // smtpServerAddress;
        25,                         // smtpServerPort
        null,                       // smtpServerUserId;
        null,                       // smtpServerPassword
        null                        // smtpServerTls
    );

    public new() {
        super(IEmailModuleCfgEntityResolver)
    }

    override public EmailModuleCfgDTO getDefaultModuleConfiguration() {
        return DEFAULT_MODULE_CFG;
    }
}
