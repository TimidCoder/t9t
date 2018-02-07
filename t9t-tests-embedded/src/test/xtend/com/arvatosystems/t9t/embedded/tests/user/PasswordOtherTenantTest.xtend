package com.arvatosystems.t9t.embedded.tests.user

import com.arvatosystems.t9t.auth.UserKey
import com.arvatosystems.t9t.auth.request.UserCrudAndSetPasswordRequest
import com.arvatosystems.t9t.auth.tests.setup.SetupUserTenantRole
import com.arvatosystems.t9t.authc.api.ResetPasswordRequest
import com.arvatosystems.t9t.base.ITestConnection
import com.arvatosystems.t9t.doc.DocConfigDTO
import com.arvatosystems.t9t.doc.DocEmailReceiverDTO
import com.arvatosystems.t9t.embedded.connect.InMemoryConnection
import de.jpaw.bonaparte.pojos.api.OperationType
import de.jpaw.util.ApplicationException
import de.jpaw.util.ExceptionUtil
import java.util.UUID
import org.junit.BeforeClass
import org.junit.Test

import static extension com.arvatosystems.t9t.auth.extensions.AuthExtensions.*
import static extension com.arvatosystems.t9t.doc.extensions.DocExtensions.*

class PasswordOtherTenantTest {
    static private ITestConnection dlg

    static private final String TEST_USER_ID = "userOT"
    static private final String TEST_EMAIL = "test@nowhere.com"

    @BeforeClass
    def public static void createConnection() {
        try {
            // use a single connection for all tests (faster)
            dlg = new InMemoryConnection

            // ignore any password reset emails...
            new DocConfigDTO => [
                documentId            = "passwordReset"
                description           = "reset password document"
                emailSettings         = new DocEmailReceiverDTO => [
                    storeEmail        = true
                ]
                merge(dlg)
            ]

            val setup = new SetupUserTenantRole(dlg)
            setup.createUserTenantRole(TEST_USER_ID, UUID.randomUUID, false)
            val user = new UserKey(TEST_USER_ID).read(dlg)
            user.emailAddress  = TEST_EMAIL
            user.merge(dlg)
            // do some resets
            dlg.okIO(new ResetPasswordRequest(TEST_USER_ID, TEST_EMAIL))
            dlg.okIO(new ResetPasswordRequest(TEST_USER_ID, TEST_EMAIL))

            // set the password to a defined value
            val rq = new UserCrudAndSetPasswordRequest => [
                crud       = OperationType.MERGE
                data       = user
                naturalKey = new UserKey(TEST_USER_ID)
                password   = "secret12345"
                validate
            ]
            dlg.okIO(rq)
        } catch (Exception e) {
            println("Exception during init: " + ExceptionUtil.causeChain(e))
        }
    }

    @Test
    def public void loginViaPresetPasswordTest() {
        new InMemoryConnection(TEST_USER_ID, "secret12345")
    }

    @Test
    def public void loginViaBadPasswordTest() {
        try {
            new InMemoryConnection(TEST_USER_ID, "secret12344")
            throw new RuntimeException("exception expected")
        } catch (ApplicationException e) {
            if (e.errorCode != 100020040)
                throw e
            // else OK, expected this exception
        }
    }
}
