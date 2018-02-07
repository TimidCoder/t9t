package com.arvatosystems.t9t.stdsetup

import com.arvatosystems.t9t.auth.ApiKeyDTO
import com.arvatosystems.t9t.auth.PermissionsDTO
import com.arvatosystems.t9t.auth.UserDTO
import com.arvatosystems.t9t.auth.UserKey
import com.arvatosystems.t9t.base.ITestConnection
import com.arvatosystems.t9t.doc.DocConfigDTO
import com.arvatosystems.t9t.doc.DocEmailReceiverDTO
import com.arvatosystems.t9t.doc.DocTemplateDTO
import com.arvatosystems.t9t.doc.api.TemplateType
import de.jpaw.annotations.AddLogger
import de.jpaw.bonaparte.pojos.api.OperationType
import de.jpaw.bonaparte.pojos.api.auth.Permissionset
import de.jpaw.bonaparte.pojos.api.media.MediaType
import de.jpaw.bonaparte.pojos.api.media.MediaXType
import java.util.UUID
import org.eclipse.xtend.lib.annotations.Data

import static extension com.arvatosystems.t9t.auth.extensions.AuthExtensions.*
import static extension com.arvatosystems.t9t.doc.extensions.DocExtensions.*

@AddLogger
@Data
class T9tEmailSetup {
    ITestConnection dlg

    static final public UUID API_KEY_FORGOT_PW     = UUID.fromString("282fd9dd-233c-4ebe-8d91-727bebd839c6");
    private final Permissionset onlyExecPermission = Permissionset.of(OperationType.EXECUTE)
    private final Permissionset execReadPermission = Permissionset.of(OperationType.EXECUTE, OperationType.READ)



    def void loadDocConfigs(String mySubject, String myEmailAddress) {
        LOGGER.info("Loading standard doc configs")

        new DocConfigDTO => [
            documentId      = 'passwordReset'
            mappedId        = 'passwordReset'
            description     = 'Password Reset Mail'
            communicationFormat = MediaXType.of(MediaType.HTML)
            emailBodyTemplateId = 'passwordReset'
            emailSettings = new DocEmailReceiverDTO => [
                emailSubject     = mySubject ?: 'Your new password'
                defaultFrom      = myEmailAddress
                defaultReplyTo   = myEmailAddress
                subjectType      = TemplateType.INLINE
            ]
            merge(dlg)
        ]
    }

    def void loadTemplates() {
        LOGGER.info("Loading standard templates")

        #[ 'passwordReset' ].forEach [ name |
            LOGGER.info("Loading template resource {}", name)
            val templateData = ("doc/template/standard/" + name + ".ftl").resourceAsHTML
            // store the templates as global defaults
            new DocTemplateDTO => [
                defaultKey
                documentId      = name
                mediaType       = templateData.mediaType
                template        = templateData.text
                it.name         = "General " + name
                merge(dlg)
            ]
        ]
    }

    def void createPWUser(String myEmailAddress) {
        LOGGER.info("Create API key and user for forgot-my-password feature")

        new UserDTO => [
            userId         = "forgotPW"
            name           = "technical user for forgotPW"
            isActive       = true
            isTechnical    = true
            emailAddress   = myEmailAddress
            merge(dlg)
        ]
        new ApiKeyDTO => [
            apiKey         = API_KEY_FORGOT_PW
            userRef        = new UserKey("forgotPW")
            name           = "API key for forgotPW"
            isActive       = true
            permissions    = new PermissionsDTO => [
                minPermissions      = onlyExecPermission
                maxPermissions      = onlyExecPermission
                resourceRestriction = "B.t9t.authc.api.GetTenants,B.t9t.authc.api.ResetPassword"
                resourceIsWildcard  = Boolean.TRUE
            ]
            merge(dlg)
        ]
    }
}
