package com.arvatosystems.t9t.base.jpa.ormspecific;

import javax.persistence.EntityManagerFactory;

import com.arvatosystems.t9t.cfg.be.RelationalDatabaseConfiguration;

public interface IEMFCustomizer {
    public EntityManagerFactory getCustomizedEmf(String puName, RelationalDatabaseConfiguration settings) throws Exception;
}
