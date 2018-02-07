package com.arvatosystems.t9t.base.jpa.jta.impl;

import com.arvatosystems.t9t.base.services.RequestContext;

import de.jpaw.bonaparte.jpa.refs.PersistenceProviderJPA;
import de.jpaw.bonaparte.pojos.api.PersistenceProviders;
import de.jpaw.dp.CustomScope;
import de.jpaw.dp.Jdp;
import de.jpaw.dp.Provider;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The provider for the JPA persistence context. This implementation hooks into
 * the RequestContext (which could be passed across threads) and checks for an
 * existing JPA provider. If none exists, it creates a new one and registers it.
 */
public class PersistenceProviderJPAProvider implements CustomScope<PersistenceProviderJPA> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceProviderJPAProvider.class);

    private final Provider<RequestContext> ctxProvider = Jdp.getProvider(RequestContext.class);
    private final EntityManager em;

    public PersistenceProviderJPAProvider(EntityManager em) {
        super();
        this.em = em;
    }

    @Override
    public PersistenceProviderJPA get() {
        RequestContext ctx = ctxProvider.get();
        PersistenceProviderJPA jpaContext = (PersistenceProviderJPA) ctx.getPersistenceProvider(PersistenceProviders.JPA.ordinal());
        if (jpaContext == null) {
            // does not exist, create a new one!
            LOGGER.trace("Adding JPA to request context");
            jpaContext = new PersistenceProviderJPAJtaImpl(em);
            ctx.addPersistenceContext(jpaContext);
        }

        return jpaContext;
    }

    @Override
    public void set(PersistenceProviderJPA instance) {
        LOGGER.warn("Set operation is not supported");
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
        LOGGER.debug("Closing JPA request context (unsupported)");
        throw new UnsupportedOperationException();
    }
}
