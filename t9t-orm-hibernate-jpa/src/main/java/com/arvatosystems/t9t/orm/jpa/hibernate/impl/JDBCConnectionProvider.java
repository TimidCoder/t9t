package com.arvatosystems.t9t.orm.jpa.hibernate.impl;

import java.sql.Connection;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;

import com.arvatosystems.t9t.base.jpa.ormspecific.IJpaJdbcConnectionProvider;

import de.jpaw.dp.Singleton;

@Singleton
public class JDBCConnectionProvider implements IJpaJdbcConnectionProvider {

    @Override
    public Connection get(EntityManager em) {
        return ((SessionImpl)em.unwrap(Session.class)).connection();
    }

}
