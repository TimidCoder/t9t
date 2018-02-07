package com.arvatosystems.t9t.base.services;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;

/** Defines a way to obtain a JDBC connection independently of the current request context. */
@Deprecated
public interface IIndependentJdbcConnectionProvider extends Closeable {

    /** Retrieves the JDBC connection of the current session. */
    Connection getJDBCConnection();

    /** Closes the connection if it exists and is open. */
    @Override
    public void close() throws IOException;

}
