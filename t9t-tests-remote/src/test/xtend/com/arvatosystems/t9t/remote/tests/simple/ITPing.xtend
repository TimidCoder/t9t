package com.arvatosystems.t9t.remote.tests.simple

import com.arvatosystems.t9t.auth.tests.setup.SetupUserTenantRole
import com.arvatosystems.t9t.authc.api.GetTenantsRequest
import com.arvatosystems.t9t.authc.api.GetTenantsResponse
import com.arvatosystems.t9t.base.request.ExecuteAsyncRequest
import com.arvatosystems.t9t.base.request.LogMessageRequest
import com.arvatosystems.t9t.base.request.PingRequest
import com.arvatosystems.t9t.remote.connect.Connection
import com.arvatosystems.t9t.remote.connect.ConnectionTypes
import com.arvatosystems.t9t.remote.connect.TcpConnection
import java.util.UUID
import org.junit.Test
import com.arvatosystems.t9t.uiprefs.request.GridConfigRequest
import com.arvatosystems.t9t.uiprefs.request.GridConfigResponse
import de.jpaw.bonaparte.util.ToStringHelper
import com.arvatosystems.t9t.base.request.BatchRequest
import com.arvatosystems.t9t.base.request.PauseRequest

class ITPing {
    @Test
    def public void pingTest() {
        val dlg = new Connection

        dlg.okIO(new PingRequest)
    }

    @Test
    def public void pingViaJsonTest() {
        val dlg = new Connection(true, ConnectionTypes.JSON)

        dlg.okIO(new PingRequest)
    }

    @Test
    def public void selectTenantsAndSwitchTenantViaJsonTest() {
        val dlg = new Connection(true, ConnectionTypes.JSON)

        val tenants = dlg.typeIO(new GetTenantsRequest, GetTenantsResponse)
        for (t: tenants.tenants) {
            println('''Tenant «t.tenantId» has ref «t.tenantRef» and name «t.name»''')
        }

        // switch tenant
        dlg.switchTenant("MOON", 0)
    }

    @Test
    def public void tcpPingTest() {
        val dlg = new TcpConnection

        dlg.okIO(new PingRequest)
    }

    @Test
    def public void pingLessLogTest() {
        val dlg = new Connection(false)

        dlg.okIO(new PingRequest)
    }

    @Test
    def public void pingTestMoreLog() {
        val dlg = new Connection(true)

        dlg.okIO(new PingRequest)
    }

    @Test
    def public void asyncPingTest() {
        val dlg = new Connection

        dlg.okIO(new ExecuteAsyncRequest(new PingRequest))
    }

    @Test
    def public void pingForNewUserTest() {
        val dlg = new Connection

        val setup = new SetupUserTenantRole(dlg)

        val newKey = UUID.randomUUID
        setup.createUserTenantRole("ping", newKey, true)

        dlg.okIO(new PingRequest)
    }

    @Test
    def public void helloTest() {
        val dlg = new Connection

        dlg.okIO(new LogMessageRequest("hello, world"))
    }

    @Test
    def public void t9tGridConfigTest() {
        val dlg = new Connection

        val result = dlg.typeIO(new GridConfigRequest => [
            gridId = "roleToPermission"
        ], GridConfigResponse)
        println('''Grid config result is «ToStringHelper.toStringML(result.gridConfig)»''')
    }

    @Test
    def public void t9tGridConfigJsonTest() {
        val dlg = new Connection(true, ConnectionTypes.JSON)

        val result = dlg.typeIO(new GridConfigRequest => [
            gridId = "roleToPermission"
        ], GridConfigResponse)
        println('''Grid config result is «ToStringHelper.toStringML(result.gridConfig)»''')
    }

    @Test
    def public void helloAsyncAsyncAsyncTest() {
        val dlg = new Connection

        dlg.okIO(
            new ExecuteAsyncRequest(
                new ExecuteAsyncRequest(
                    new ExecuteAsyncRequest(
                        new ExecuteAsyncRequest(
                            new ExecuteAsyncRequest(
                                new ExecuteAsyncRequest(
                                    new LogMessageRequest("hello, world")
                                )
                            )
                        )
                    )
                )
            )
        )
    }

    @Test
    def public void tryProcessStatusTest() {
        val dlg = new Connection

        dlg.okIO(
            new BatchRequest => [
                commands = #[
                    new LogMessageRequest("hello, world"),
                    new PauseRequest => [ delayInMillis = 3000 ],
                    new LogMessageRequest("new message"),
                    new PauseRequest => [ delayInMillis = 3000 ],
                    new LogMessageRequest("processing X"),
                    new PauseRequest => [ delayInMillis = 3000 ],
                    new LogMessageRequest("processing ZZZ")
                ]
            ]
        )
    }
}
