package com.arvatosystems.t9t.ssm.be.impl

import com.arvatosystems.t9t.base.services.IJdbcConnectionProvider
import de.jpaw.annotations.AddLogger
import de.jpaw.dp.Inject
import de.jpaw.dp.Named
import java.sql.SQLException
import org.quartz.utils.ConnectionProvider

// custom connection provider, used to avoid that connection information has to be maintained multiple times
// (for the JPA and for quartz)
@AddLogger
class JdbcConnectionProvider implements ConnectionProvider {
    @Inject @Named("independent") IJdbcConnectionProvider provider

    override getConnection() throws SQLException {
        return provider.JDBCConnection
    }

    override initialize() throws SQLException {
        LOGGER.info("Quartz connection provider initializing")
    }

    override shutdown() throws SQLException {
        LOGGER.info("Quartz connection provider shutting down")
    }
}
