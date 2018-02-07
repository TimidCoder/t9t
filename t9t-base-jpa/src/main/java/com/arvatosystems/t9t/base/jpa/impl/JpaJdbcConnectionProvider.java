package com.arvatosystems.t9t.base.jpa.impl;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.arvatosystems.t9t.base.jpa.ormspecific.IJpaJdbcConnectionProvider;
import com.arvatosystems.t9t.base.services.IJdbcConnectionProvider;

import de.jpaw.bonaparte.jpa.refs.PersistenceProviderJPA;
import de.jpaw.dp.Jdp;
import de.jpaw.dp.Provider;
import de.jpaw.dp.Singleton;

@Singleton
public class JpaJdbcConnectionProvider implements IJdbcConnectionProvider {
    private static AtomicInteger counter = new AtomicInteger();

    protected final Provider<PersistenceProviderJPA> jpaContextProvider = Jdp.getProvider(PersistenceProviderJPA.class);
    protected final IJpaJdbcConnectionProvider connProvider = Jdp.getRequired(IJpaJdbcConnectionProvider.class);

    @Override
    public Connection getJDBCConnection() {
        counter.incrementAndGet();
        return connProvider.get(jpaContextProvider.get().getEntityManager());
    }

    @Override
    public List<Integer> checkHealth() {
        Integer count = counter.get();
        return Collections.singletonList(count);
    }
}
