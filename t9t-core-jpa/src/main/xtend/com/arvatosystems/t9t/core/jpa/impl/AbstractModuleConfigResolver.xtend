package com.arvatosystems.t9t.core.jpa.impl

import com.arvatosystems.t9t.base.T9tConstants
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion
import com.arvatosystems.t9t.base.jpa.IResolverLongKey42
import com.arvatosystems.t9t.base.moduleCfg.ModuleConfigDTO
import com.arvatosystems.t9t.base.services.ICacheInvalidationRegistry
import com.arvatosystems.t9t.core.jpa.entities.ModuleConfigEntity
import com.arvatosystems.t9t.server.services.IModuleConfigResolver
import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import de.jpaw.annotations.AddLogger
import de.jpaw.bonaparte.jpa.BonaPersistableData
import de.jpaw.dp.Inject
import de.jpaw.dp.Jdp
import java.util.concurrent.TimeUnit

/** Implementation of a class which reads module tenant configuration (and caches the entries). */
@AddLogger
abstract class AbstractModuleConfigResolver<D extends ModuleConfigDTO, E extends ModuleConfigEntity & BonaPersistableData<D>>
 implements T9tConstants, IModuleConfigResolver<D> {
    private final IResolverLongKey42<FullTrackingWithVersion, E> resolver
    private String query
    private final Cache<Long,D> dtoCache
    @Inject ICacheInvalidationRegistry cacheInvalidationRegistry


    protected new(Class<? extends IResolverLongKey42<FullTrackingWithVersion, E>> resolverClass) {
        resolver = Jdp.getRequired(resolverClass)
        dtoCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build
        cacheInvalidationRegistry.registerInvalidator(resolver.baseJpaEntityClass.simpleName, [ dtoCache.invalidateAll ])
        LOGGER.info("Created ModuleConfigResolver for {}", resolver.baseJpaEntityClass.simpleName)
    }

    /**
     * {@inheritDoc }
     */
    override D getModuleConfiguration() {
        val tenantRef = resolver.sharedTenantRef
        val cacheHit = dtoCache.getIfPresent(tenantRef)
        if (cacheHit !== null)
            return cacheHit;
        // not in cache: read database
        val em = resolver.entityManager
        val tenants = if (GLOBAL_TENANT_REF42.equals(tenantRef)) #[ GLOBAL_TENANT_REF42 ] else #[ GLOBAL_TENANT_REF42, tenantRef ]
        query    = '''SELECT e FROM «resolver.entityClass.simpleName» e WHERE e.tenantRef IN :tenants ORDER BY e.tenantRef DESC'''

        var D result = getDefaultModuleConfiguration
        val quer = em.createQuery(query, resolver.entityClass)
        quer.setParameter("tenants", tenants)
        try {
            val results = quer.resultList
            if (!results.nullOrEmpty) {
                LOGGER.debug("Found entry for ModuleConfigResolver cache {} for tenantRef {}", resolver.baseJpaEntityClass.simpleName, tenantRef)
                result = results.get(0).ret$Data
            }
        } catch (Exception e) {
            LOGGER.error("JPA exception {} while reading module configuration for {} for tenantRef {}: {}",
                e.class.simpleName, resolver.entityClass.simpleName, tenantRef, e.message);
        }
        result.freeze  // make immutable
        dtoCache.put(tenantRef, result)
        LOGGER.debug("Updating ModuleConfigResolver cache {} for tenantRef {}", resolver.baseJpaEntityClass.simpleName, tenantRef)
        return result
    }

    override updateModuleConfiguration(D cfg) {
        val E newEntity = resolver.newEntityInstance
        val tenantRef = resolver.sharedTenantRef
        newEntity.put$Data(cfg)
        newEntity.put$Key(tenantRef)
        newEntity.tenantRef = tenantRef
        resolver.entityManager.merge(newEntity)   // resolver.save(newEntity) would do this and some of the previous assignments...
        cfg.freeze
        dtoCache.put(tenantRef, cfg)  // update the cache
    }
}
