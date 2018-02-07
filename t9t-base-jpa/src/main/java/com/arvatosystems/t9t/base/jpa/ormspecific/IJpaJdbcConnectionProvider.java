package com.arvatosystems.t9t.base.jpa.ormspecific;

import java.sql.Connection;

import javax.persistence.EntityManager;

public interface IJpaJdbcConnectionProvider {

    /**
     * Get the actual JDBC Connection of the current context. The implementation is ORM specific.
     *
     * @return the actual JDBC connection
     */
    Connection get(EntityManager em);
}
