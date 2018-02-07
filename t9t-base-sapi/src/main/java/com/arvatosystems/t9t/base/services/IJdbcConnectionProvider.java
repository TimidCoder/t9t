package com.arvatosystems.t9t.base.services;

import java.sql.Connection;
import java.util.List;

/** Defines a way to obtain a JDBC connection from some persistence layer built on top of it, for example JPA.
 * Inject with @Named("independent") to get connections which are not tied to the current session. */
public interface IJdbcConnectionProvider {
    /** Retrieves the JDBC connection of the current session. */
    Connection getJDBCConnection();
    List<Integer> checkHealth();
}
