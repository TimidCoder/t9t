package com.arvatosystems.t9t.orm.jpa.eclipselink.impl;

import java.sql.Connection;

import javax.persistence.EntityManager;

import com.arvatosystems.t9t.base.jpa.ormspecific.IJpaJdbcConnectionProvider;

import de.jpaw.dp.Singleton;

@Singleton
public class JDBCConnectionProvider implements IJpaJdbcConnectionProvider {

    @Override
    public Connection get(EntityManager em) {
        return em.unwrap(Connection.class);
    }

}
