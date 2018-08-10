package com.arvatosystems.t9t.auth.jpa.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.auth.jpa.entities.TenantEntity;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Startup;

/**
 * Performs a query on the tenants table.
 * The result is discarded.
 * The query is to to ensure that all layers of the JPA to database connectivity have been used and are initialized.
 * Not doing this would work as well, but obfuscate subsequent logs (because the initialization logs could appear in asynchronous threads then.)
 */
@Startup(30003)
public class LifecycleJpaLaunch {
    private static final Logger LOGGER = LoggerFactory.getLogger(LifecycleJpaLaunch.class);

    public static void onStartup() {
        LOGGER.info("Initial query to initialize connection pool STARTED");

        final EntityManagerFactory emf = Jdp.getRequired(EntityManagerFactory.class);
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<TenantEntity> query = em.createQuery("SELECT q FROM TenantEntity q", TenantEntity.class);
        query.setMaxResults(1);
        query.getResultList();
        em.getTransaction().commit();
        em.clear();
        em.close();
        LOGGER.info("Initial query to initialize connection pool DONE");
    }
}
